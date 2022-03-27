package cn.lliiooll.mt.beans.yggdrasil;


import lombok.*;


/**
 * https://github.com/yushijinhun/authlib-injector/wiki/Yggdrasil-%E6%9C%8D%E5%8A%A1%E7%AB%AF%E6%8A%80%E6%9C%AF%E8%A7%84%E8%8C%83#%E9%94%99%E8%AF%AF%E4%BF%A1%E6%81%AF%E6%A0%BC%E5%BC%8F
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserInfoBean extends BaseYggBean {

    /**
     * uuid
     */
    private String id;

    private ProfileInfoBean.ProfileProperties[] properties;

}
