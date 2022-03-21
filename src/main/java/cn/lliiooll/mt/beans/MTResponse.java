package cn.lliiooll.mt.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MTResponse {

    public int code;
    public String msg;
    public Object data;
}
