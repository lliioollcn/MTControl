package cn.lliiooll.mt.beans.yggdrasil;


import lombok.*;

/**
 * https://github.com/yushijinhun/authlib-injector/wiki/Yggdrasil-%E6%9C%8D%E5%8A%A1%E7%AB%AF%E6%8A%80%E6%9C%AF%E8%A7%84%E8%8C%83#%E8%A7%92%E8%89%B2%E4%BF%A1%E6%81%AF%E7%9A%84%E5%BA%8F%E5%88%97%E5%8C%96
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProfileInfoBean extends BaseYggBean {

    /**
     * uuid
     */
    private String id;
    private String name;

    private ProfileProperties[] properties;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileProperties {
        private String name;
        private String signature;
        private Object value;
    }
}
