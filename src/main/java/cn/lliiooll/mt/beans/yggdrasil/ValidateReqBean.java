package cn.lliiooll.mt.beans.yggdrasil;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ValidateReqBean extends BaseYggBean {

    private String accessToken;
    private String clientToken;

}
