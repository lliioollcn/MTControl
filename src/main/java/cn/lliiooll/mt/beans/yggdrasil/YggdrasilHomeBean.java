package cn.lliiooll.mt.beans.yggdrasil;


import lombok.*;

/**
 * https://github.com/yushijinhun/authlib-injector/wiki/Yggdrasil-%E6%9C%8D%E5%8A%A1%E7%AB%AF%E6%8A%80%E6%9C%AF%E8%A7%84%E8%8C%83#API%20%E5%85%83%E6%95%B0%E6%8D%AE%E8%8E%B7%E5%8F%96
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class YggdrasilHomeBean extends BaseYggBean {

    private HomeMetadataBean meta;
    private String[] skinDomains;
    private String signaturePublickey;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HomeMetadataBean {
        private String serverName;
        private String implementationName;
        private String implementationVersion;
        private HomeMetadataLinksBean links;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class HomeMetadataLinksBean {
            private String homepage;
            private String register;

        }

    }
}
