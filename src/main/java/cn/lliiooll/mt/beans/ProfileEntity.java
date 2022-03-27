package cn.lliiooll.mt.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEntity implements Serializable{

        private String uuid;
        private String name;
        private String accessToken;
        private String serverId;
        private String ip;
        private long createTime;
}
