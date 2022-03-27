package cn.lliiooll.mt.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public int Id;
    public String role;
    public int banned;
    public String username;
    public String password;
    public String uid;
    public String email;
    public String lastip;
    public long lasttime;
    public String qq;
    public String phone;
}
