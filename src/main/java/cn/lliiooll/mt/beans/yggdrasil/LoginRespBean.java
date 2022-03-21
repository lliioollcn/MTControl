package cn.lliiooll.mt.beans.yggdrasil;


import lombok.*;


/**
 * https://github.com/yushijinhun/authlib-injector/wiki/Yggdrasil-%E6%9C%8D%E5%8A%A1%E7%AB%AF%E6%8A%80%E6%9C%AF%E8%A7%84%E8%8C%83#%E7%99%BB%E5%BD%95
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginRespBean extends BaseYggBean {

    private String accessToken;
    private String clientToken;
    private ProfileInfoBean[] availableProfiles;
    private ProfileInfoBean selectedProfile;
    private UserInfoBean[] user;
}
