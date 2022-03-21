package cn.lliiooll.mt.utils;

import cn.lliiooll.mt.beans.MTResponse;

public class RespUtils {
    public static MTResponse error(String msg) {
        return error(msg, null);
    }

    public static MTResponse error(String msg, Object data) {
        return resp(msg, data, MTResponseCodes.FAILED);
    }

    public static MTResponse success(String msg) {
        return success(msg, null);
    }

    public static MTResponse success(String msg, Object data) {
        return resp(msg, data, MTResponseCodes.SUCCESS);
    }

    public static MTResponse jump(String path) {
        return resp("none", path, MTResponseCodes.JUMP);
    }


    public static MTResponse resp(String msg, Object data, int code) {
        return MTResponse.builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }
}
