package com.aspen.core.foundation.enums;

import lombok.Getter;

/**
 * 返回结果枚举
 * @author jingchuan
 */
@Getter
public enum ResultStatusEnum {

    /**
     *
     */
    SUCCESS(10000, "成功"),
    ERROR(10001, "操作失败"),
    LOGIN_ERROR(10002, "登录失败"),
    LOGIN_EXPIRE(10003, "请重新登录"),
    NOT_FOUND(10004, "资源不存在"),
    NO_PERMISSION(10005, "权限不足"),
    ;

    private final Integer code;
    private final String msg;

    ResultStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
