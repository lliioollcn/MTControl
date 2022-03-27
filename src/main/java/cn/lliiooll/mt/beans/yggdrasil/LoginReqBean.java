package cn.lliiooll.mt.beans.yggdrasil;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginReqBean extends BaseYggBean {

    private String username;
    private String password;
    private String clientToken;
    private boolean requestUser;
    private LoginReqAgentBean agent;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class LoginReqAgentBean extends BaseYggBean {
        private String name;
        private int version;
    }
}
