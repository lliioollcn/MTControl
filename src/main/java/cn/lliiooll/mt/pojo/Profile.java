package cn.lliiooll.mt.pojo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    public String uuid;
    public String name;
    public String uid;
    public String accessToken;
    public String clientToken;
    public String serverId;
    public long lastTime;
    public String lastIp;
    public String texturesUuid;
}
