package cn.lliiooll.mt.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import cn.lliiooll.mt.beans.ProfileEntity;
import cn.lliiooll.mt.beans.YggdrasilResp;
import cn.lliiooll.mt.beans.yggdrasil.*;
import cn.lliiooll.mt.pojo.Profile;
import cn.lliiooll.mt.pojo.ProfileTextures;
import cn.lliiooll.mt.pojo.UserRole;
import cn.lliiooll.mt.service.ProfileService;
import cn.lliiooll.mt.service.TexturesService;
import cn.lliiooll.mt.service.UserService;
import cn.lliiooll.mt.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping(value = "/yggdrasil", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class YggdrasilController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private TexturesService texturesService;
    @Autowired
    private BCryptPasswordEncoder pwEncoder;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisUtil redis;

    @PostMapping("/authserver/authenticate")
    public YggdrasilResp authenticate(@RequestBody LoginReqBean req) {
        if (!ObjectUtils.isNulls(req.getAgent(), req.getPassword(), req.getUsername(), req.getAgent().getName(), req.getPassword())) {
            final UserRole role = userService.findUserByEmail(req.getUsername());
            if (!Objects.isNull(role) && pwEncoder.matches(req.getPassword(), role.getPassword())) {
                final List<Profile> profiles = profileService.findProfileByUid(role.getUid());
                if (StrUtil.isBlank(req.getClientToken())) {
                    req.setClientToken(YggdrasilUtils.genClientToken());
                }
                if (profiles.size() < 1) {
                    Profile p = Profile.builder()
                            .texturesUuid("base")
                            .uuid(YggdrasilUtils.genUuid(role.getUsername()))
                            .uid(role.getUid())
                            .serverId(null)
                            .name(role.getUsername())
                            .lastIp(SpringUtils.getIpAddr(request))
                            .clientToken(req.getClientToken())
                            .accessToken(YggdrasilUtils.genAccessToken(role.getUsername(), req.getClientToken()))
                            .lastTime(System.currentTimeMillis())
                            .build();
                    profiles.add(p);
                    profileService.createProfile(p);
                }
                Profile profile = profiles.get(0);
                if (System.currentTimeMillis() - profile.getLastTime() > 1000 * 60 * 60 * 34 * 3) {
                    profile.setAccessToken(YggdrasilUtils.genAccessToken(profile.getName(), profile.getClientToken()));
                }
                profile.setLastTime(System.currentTimeMillis());
                profile.setLastIp(SpringUtils.getIpAddr(request));
                profileService.updateProfile(profile);
                LoginRespBean resp = LoginRespBean.builder()
                        .accessToken(profiles.get(0).getAccessToken())
                        .clientToken(req.getClientToken())
                        .selectedProfile(ProfileInfoBean.builder()
                                .id(profile.getUuid())
                                .name(profile.getName())
                                /*
                                .properties(new ArrayList<ProfileInfoBean.ProfileProperties>() {{
                                    ProfileTextures textures = texturesService.findTexturesByUuid(profile.getTexturesUuid());
                                    add(ProfileInfoBean.ProfileProperties.builder()
                                            .name("textures")
                                            .value(TexturesBean.builder()
                                                    .timestamp(textures.getTimestamp())
                                                    .profileId(profile.getUuid())
                                                    .profileName(profile.getName())
                                                    .textures(new HashMap<>() {{
                                                        put(YggdrasilUtils.TexturesType.SKIN, TexturesBean.TexturesDetailsBean.builder()
                                                                .url(YggdrasilUtils.URL + textures.getSkinUrl())
                                                                .metadata(null)
                                                                .build());
                                                    }})
                                                    .build())
                                            .build());
                                }}.toArray(new ProfileInfoBean.ProfileProperties[0]))

                                 */
                                .properties(null)
                                .build())
                        .availableProfiles(new ArrayList<ProfileInfoBean>() {{
                            for (final Profile profile : profiles) {
                                add(ProfileInfoBean.builder()
                                        .id(profile.getUuid())
                                        .name(profile.getName())
                                        .properties(null)
                                        .build());
                            }
                        }}.toArray(new ProfileInfoBean[0]))
                        .build();
                if (req.isRequestUser()) {
                    Profile profile1 = profiles.get(0);
                    resp.setUser(UserInfoBean.builder()
                            .id(profile1.getUuid())
                            .properties(new ProfileInfoBean.ProfileProperties[0])
                            .build());
                }
                return YggdrasilResp.create(resp);
            }
        }
        return YggdrasilResp.create(YggdrasilResp.Status.ERROR, ErrorBean.builder().cause("无效的用户名/密码").errorMessage("Invalid username or password.").error("ForbiddenOperationException").build());

    }

    @RequestMapping("/authserver/refresh")
    public YggdrasilResp refresh(@RequestBody RefreshReqBean req) {
        if (!ObjectUtils.isNulls(req.getAccessToken(), req.getClientToken())) {
            final Profile profile = profileService.findProfileByAccessToken(req.getAccessToken());
            if (!Objects.isNull(profile)) {
                if (StrUtil.isBlank(req.getClientToken())) {
                    req.setClientToken(YggdrasilUtils.genClientToken());
                }
                if (!Objects.isNull(req.getSelectedProfile())) {
                    Profile nP = profileService.findProfileByName(req.getSelectedProfile().getName());
                    Profile uP = profileService.findProfileByUUID(req.getSelectedProfile().getId());
                    if (Objects.isNull(nP) && Objects.isNull(uP)) {
                        profile.setName(req.getSelectedProfile().getName());
                        profile.setUuid(req.getSelectedProfile().getId());
                    }
                }
                profile.setAccessToken(YggdrasilUtils.genAccessToken(profile.getName(), profile.getClientToken()));
                profile.setLastTime(System.currentTimeMillis());
                profile.setLastIp(SpringUtils.getIpAddr(request));
                profileService.updateProfile(profile);
                LoginRespBean resp = LoginRespBean.builder()
                        .accessToken(profile.getAccessToken())
                        .clientToken(req.getClientToken())
                        .selectedProfile(ProfileInfoBean.builder()
                                .name(profile.getName())
                                .id(profile.getUuid())
                                .build())
                        .availableProfiles(null)
                        .build();
                if (req.isRequestUser()) {
                    resp.setUser(UserInfoBean.builder()
                            .id(profile.getUuid())
                            .properties(new ProfileInfoBean.ProfileProperties[0])
                            .build());
                }
                return YggdrasilResp.create(resp);
            }
        }
        return YggdrasilResp.create(YggdrasilResp.Status.ERROR, ErrorBean.builder().cause("无效的Token").errorMessage("Invalid token.").error("ForbiddenOperationException").build());

    }

    @RequestMapping("/authserver/validate")
    public YggdrasilResp validate(@RequestBody ValidateReqBean req) {
        if (!ObjectUtils.isNulls(req.getAccessToken())) {
            final Profile profile = profileService.findProfileByAccessToken(req.getAccessToken());
            if (!Objects.isNull(profile)) {
                if (req.getAccessToken().equalsIgnoreCase(profile.getAccessToken())) {
                    if (StrUtil.isBlank(req.getClientToken())) {
                        req.setClientToken(YggdrasilUtils.genClientToken());
                    }
                    profile.setLastTime(System.currentTimeMillis());
                    profile.setLastIp(SpringUtils.getIpAddr(request));
                    profile.setClientToken(req.getClientToken());
                    profileService.updateProfile(profile);
                    return YggdrasilResp.create(YggdrasilResp.Status.VALIDATED);
                }
            }
        }
        return YggdrasilResp.create(YggdrasilResp.Status.ERROR, ErrorBean.builder().cause("无效的Token").errorMessage("Invalid token.").error("ForbiddenOperationException").build());
    }

    @RequestMapping("/authserver/invalidate")
    public YggdrasilResp invalidate(@RequestBody ValidateReqBean req) {
        if (!ObjectUtils.isNulls(req.getAccessToken())) {
            final Profile profile = profileService.findProfileByAccessToken(req.getAccessToken());
            if (!Objects.isNull(profile)) {
                if (req.getAccessToken().equalsIgnoreCase(profile.getAccessToken())) {
                    if (StrUtil.isBlank(req.getClientToken())) {
                        req.setClientToken(YggdrasilUtils.genClientToken());
                    }
                    profile.setLastTime(System.currentTimeMillis());
                    profile.setLastIp(SpringUtils.getIpAddr(request));
                    profile.setClientToken(req.getClientToken());
                    profile.setAccessToken(null);
                    profileService.updateProfile(profile);
                }
            }
        }
        return YggdrasilResp.create(YggdrasilResp.Status.VALIDATED);
    }

    @RequestMapping("/authserver/signout")
    public YggdrasilResp signout(@RequestBody LoginReqBean req) {
        if (!ObjectUtils.isNulls(req.getUsername(), req.getPassword())) {
            final UserRole role = userService.findUserByEmail(req.getUsername());
            if (!Objects.isNull(role)) {
                if (pwEncoder.matches(req.getPassword(), role.getPassword())) {
                    List<Profile> profiles = profileService.findProfileByUid(role.getUid());
                    for (Profile profile : profiles) {
                        profile.setAccessToken(null);
                        profile.setClientToken(null);
                        profile.setLastTime(System.currentTimeMillis());
                        profile.setLastIp(SpringUtils.getIpAddr(request));
                        profileService.updateProfile(profile);
                    }
                    return YggdrasilResp.create(YggdrasilResp.Status.VALIDATED);
                }
            }
        }
        return YggdrasilResp.create(YggdrasilResp.Status.ERROR, ErrorBean.builder().cause("无效的Token").errorMessage("Invalid token.").error("ForbiddenOperationException").build());
    }

    @RequestMapping("/sessionserver/session/minecraft/join")
    public YggdrasilResp join(@RequestBody JoinReqBean req) {
        if (!ObjectUtils.isNulls(req.getAccessToken(), req.getSelectedProfile(), req.getServerId())) {
            final Profile profile = profileService.findProfileByAccessToken(req.getAccessToken());
            if (!Objects.isNull(profile)) {
                log.info("玩家试图加入服务器: {} --- {},({})", req.getAccessToken(), req.getSelectedProfile(), req.getServerId());
                log.info("玩家试图加入服务器: {}", profile);
                if (profile.getUuid().equalsIgnoreCase(req.getSelectedProfile())) {
                    ProfileEntity entity = ProfileEntity.builder()
                            .accessToken(req.getAccessToken())
                            .createTime(System.currentTimeMillis())
                            .ip(SpringUtils.getIpAddr(request))
                            .name(profile.getName())
                            .serverId(req.getServerId())
                            .uuid(profile.getUuid())
                            .build();
                    log.info("写入到缓存: {}", entity);
                    redis.set(req.getServerId(), entity, 600L * 1000L);
                    return YggdrasilResp.create(YggdrasilResp.Status.VALIDATED);
                }
            }
        }
        return YggdrasilResp.create(YggdrasilResp.Status.ERROR, ErrorBean.builder().cause("无效的用户名/密码").errorMessage("Invalid username or password.").error("ForbiddenOperationException").build());
    }

    @RequestMapping("/sessionserver/session/minecraft/hasJoined")
    public YggdrasilResp hasJoined(String username, String serverId, String ip) {
        log.info("服务器试图验证玩家: {},{},{}", username, serverId, ip);
        if (!ObjectUtils.isNulls(username, serverId)) {
            final ProfileEntity profile = (ProfileEntity) redis.get(serverId);
            log.info("服务器试图验证玩家: {}", profile);
            if (!Objects.isNull(profile)) {
                final Profile uProfile = profileService.findProfileByAccessToken(profile.getAccessToken());
                log.info("服务器试图验证玩家: {}", uProfile);
                if (!Objects.isNull(uProfile) && profile.getName().equalsIgnoreCase(username)) {
                    log.info("服务器试图验证玩家: {} --- {},({})", username, ip, serverId);
                    redis.del(serverId);
                    ProfileInfoBean bean = ProfileInfoBean.builder()
                            .id(profile.getUuid())
                            .name(profile.getName())
                            .properties(new ArrayList<ProfileInfoBean.ProfileProperties>() {{
                                ProfileTextures textures = texturesService.findTexturesByUuid(uProfile.getTexturesUuid());
                                TexturesBean texture = TexturesBean.builder()
                                        .timestamp(textures.getTimestamp())
                                        .profileId(profile.getUuid())
                                        .profileName(profile.getName())
                                        .textures(new HashMap<>() {{
                                            put(YggdrasilUtils.TexturesType.SKIN, TexturesBean.TexturesDetailsBean.builder()
                                                    .url(YggdrasilUtils.URL + textures.getSkinUrl())
                                                    .metadata(null)
                                                    .build());
                                        }}).build();
                                add(ProfileInfoBean.ProfileProperties.builder()
                                        .name("textures")
                                        .value(Base64.encode(JSONUtil.toJsonStr(texture), StandardCharsets.UTF_8))
                                        .signature(YggdrasilUtils.sign(texture))
                                        .build());
                            }}.toArray(new ProfileInfoBean.ProfileProperties[0]))
                            .build();
                    log.info("{}", bean);
                    return YggdrasilResp.create(bean);
                }
            }
        }
        return YggdrasilResp.create(YggdrasilResp.Status.VALIDATED);
    }

    @RequestMapping("/sessionserver/session/minecraft/profile/{uuid}")
    public YggdrasilResp hasJoined(boolean unsigned, @PathVariable("uuid") String uuid) {
        if (!ObjectUtils.isNulls(uuid)) {
            final Profile profile = profileService.findProfileByAccessToken(uuid);
            if (!Objects.isNull(profile)) {
                ProfileInfoBean bean = ProfileInfoBean.builder()
                        .id(profile.getUuid())
                        .name(profile.getName())
                        .properties(new ArrayList<ProfileInfoBean.ProfileProperties>() {{
                            ProfileTextures textures = texturesService.findTexturesByUuid(profile.getTexturesUuid());
                            TexturesBean texture = TexturesBean.builder()
                                    .timestamp(textures.getTimestamp())
                                    .profileId(profile.getUuid())
                                    .profileName(profile.getName())
                                    .textures(new HashMap<>() {{
                                        put(YggdrasilUtils.TexturesType.SKIN, TexturesBean.TexturesDetailsBean.builder()
                                                .url(YggdrasilUtils.URL + textures.getSkinUrl())
                                                .metadata(null)
                                                .build());
                                    }}).build();
                            add(ProfileInfoBean.ProfileProperties.builder()
                                    .name("textures")
                                    .value(texture)
                                    .signature(unsigned ? null : YggdrasilUtils.sign(texture))
                                    .build());
                        }}.toArray(new ProfileInfoBean.ProfileProperties[0]))
                        .build();
                log.info("试图获取玩家: {}", bean.getName());
                log.info("{}", bean);
                return YggdrasilResp.create(bean);
            }
        }
        return YggdrasilResp.create(YggdrasilResp.Status.VALIDATED);
    }

    @RequestMapping("/")
    public YggdrasilHomeBean home() {
        return YggdrasilHomeBean.builder().meta(YggdrasilHomeBean.HomeMetadataBean.builder().implementationName("MTControl").implementationVersion("v1.0").links(YggdrasilHomeBean.HomeMetadataBean.HomeMetadataLinksBean.builder().homepage(YggdrasilUtils.URL).register(YggdrasilUtils.URL + "/register").build()).serverName("MTControl Studio Server").build()).skinDomains(new ArrayList<String>() {{
            add(YggdrasilUtils.URL);
        }}.toArray(new String[0])).signaturePublickey(YggdrasilUtils.publicKey).build();
    }
}
