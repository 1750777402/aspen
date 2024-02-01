package com.aspen.core.foundation.utils;

import com.aspen.core.foundation.enums.ResultStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果
 * @author jingchuan
 */
@Data
public class AspenResponse<T> implements Serializable {

    private static final long serialVersionUID = 3787603712963451719L;

    private Integer code;

    private String msg;

    private T data;

    public AspenResponse(ResultStatusEnum resultStatusEnum, T data) {
        this.code = resultStatusEnum.getCode();
        this.msg = resultStatusEnum.getMsg();
        this.data = data;
    }

    public AspenResponse(ResultStatusEnum resultStatusEnum) {
        this.code = resultStatusEnum.getCode();
        this.msg = resultStatusEnum.getMsg();
    }

    public AspenResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> AspenResponse<T> success(T data) {
        return new AspenResponse<>(ResultStatusEnum.SUCCESS, data);
    }

    public static <T> AspenResponse<T> error() {
        return new AspenResponse<>(ResultStatusEnum.ERROR);
    }

    public static <T> AspenResponse<T> error(String msg) {
        return new AspenResponse<>(ResultStatusEnum.ERROR.getCode(), msg);
    }

    public static <T> AspenResponse<T> error(ResultStatusEnum resultStatusEnum, String msg) {
        return new AspenResponse<>(resultStatusEnum.getCode(), msg);
    }



}
