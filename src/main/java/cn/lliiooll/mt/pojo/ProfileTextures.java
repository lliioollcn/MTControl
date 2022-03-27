package cn.lliiooll.mt.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileTextures {

    private long timestamp;
    public String uuid;
    public String name;
    public String skinUrl;
}
