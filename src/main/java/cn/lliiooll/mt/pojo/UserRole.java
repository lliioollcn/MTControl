package cn.lliiooll.mt.pojo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRole extends User {

    public String perms;
}
