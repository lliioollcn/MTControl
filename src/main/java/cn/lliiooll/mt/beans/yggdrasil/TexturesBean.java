package cn.lliiooll.mt.beans.yggdrasil;


import cn.lliiooll.mt.utils.YggdrasilUtils;
import lombok.*;

import java.util.Map;

/**
 * https://github.com/yushijinhun/authlib-injector/wiki/Yggdrasil-%E6%9C%8D%E5%8A%A1%E7%AB%AF%E6%8A%80%E6%9C%AF%E8%A7%84%E8%8C%83#textures-%E6%9D%90%E8%B4%A8%E4%BF%A1%E6%81%AF%E5%B1%9E%E6%80%A7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TexturesBean extends BaseYggBean {


    private long timestamp;
    /**
     * 玩家uuid(无符号)
     */
    private String profileId;
    private String profileName;
    private Map<YggdrasilUtils.TexturesType, TexturesDetailsBean> textures;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TexturesDetailsBean {

        private String url;
        private Object metadata;

    }
}
