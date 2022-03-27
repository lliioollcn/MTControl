package cn.lliiooll.mt.beans;

import cn.lliiooll.mt.beans.yggdrasil.BaseYggBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class YggdrasilResp extends ResponseEntity<BaseYggBean> {
    private final BaseYggBean bean;

    public YggdrasilResp(HttpStatus status, BaseYggBean bean) {
        super(bean, status);
        this.bean = bean;
    }

    public YggdrasilResp(BaseYggBean bean) {
        this(HttpStatus.OK, bean);
    }

    public YggdrasilResp(HttpStatus status) {
        this(status, null);
    }


    public static YggdrasilResp create(Status status, BaseYggBean bean) {
        return switch (status) {

            case ERROR -> new YggdrasilResp(HttpStatus.FORBIDDEN, bean);
            case SUCCESS -> new YggdrasilResp(bean);
            case ASSIGNED -> new YggdrasilResp(HttpStatus.BAD_REQUEST, bean);
            case VALIDATED -> new YggdrasilResp(HttpStatus.NO_CONTENT);
        };
    }

    public static YggdrasilResp create(Status status) {
        return create(status, null);
    }

    public static YggdrasilResp create(BaseYggBean bean) {
        return create(Status.SUCCESS, bean);
    }

    public enum Status {
        ERROR, SUCCESS, ASSIGNED, VALIDATED
    }
}
