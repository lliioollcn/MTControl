package cn.lliiooll.mt.service.impl;

import cn.lliiooll.mt.mapper.ProfileMapper;
import cn.lliiooll.mt.mapper.RoleMapper;
import cn.lliiooll.mt.pojo.Profile;
import cn.lliiooll.mt.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileMapper mapper;

    @Override
    public List<Profile> findProfileByUid(String uid) {
        return mapper.findProfileByUid(uid);
    }

    @Override
    public List<Profile> findProfileByName(String name) {
        return mapper.findProfileByName(name);
    }

    @Override
    public List<Profile> findProfileByUUID(String uuid) {
        return mapper.findProfileByUUID(uuid);
    }

    @Override
    public List<Profile> findProfileByAccessToken(String accessToken) {
        return mapper.findProfileByAccessToken(accessToken);
    }

    @Override
    public List<Profile> findProfileByClientToken(String clientToken) {
        return mapper.findProfileByClientToken(clientToken);
    }

    @Override
    public List<Profile> findProfileByServerId(String serverId) {
        return mapper.findProfileByServerId(serverId);
    }

    @Override
    public void createProfile(Profile profile) {
        mapper.createProfile(profile);
    }

    @Override
    public void updateProfile(Profile profile) {
        mapper.updateProfile(profile);
    }
}
