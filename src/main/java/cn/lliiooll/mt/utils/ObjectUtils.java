package cn.lliiooll.mt.utils;

import java.util.Objects;

public class ObjectUtils {
    public static boolean isNulls(Object... objs) {
        boolean isNull = false;
        for (Object obj : objs) {
            if (Objects.isNull(obj)) {
                isNull = true;
                break;
            }
        }
        return isNull;
    }
}
