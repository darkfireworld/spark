package org.dfw.spark.core.dto;

import java.io.Serializable;

/**
 * 通用的错误代码, 为<strong>负整数</strong>
 */
public enum DtoInternalCode implements Serializable {

    /**
     * 服务器错误,错误代码：code:-1
     *
     * @value
     */
    INTERNAL_ERROR(-1, "INTERNAL_ERROR"),
    /**
     * 验证失败,错误代码：code:-2
     *
     * @value
     */
    AUTH_INVALID(-2, "AUTH_INVALID");

    int code;
    String msg;

    DtoInternalCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
