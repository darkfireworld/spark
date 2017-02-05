package org.dfw.spark.core.dto;

import org.dfw.spark.core.annotation.NotNull;
import org.dfw.spark.core.annotation.Nullable;

import java.io.Serializable;

/**
 * 通用错误接口
 * <pre>
 *  <ul>
 *      <li>code < 0 => 内部错误</li>
 *      <li>code = 0 => 正确</li>
 *      <li>code >0 => 业务错误</li>
 *  </ul>
 * </pre>
 */
public class Dto<T> implements Serializable {
    static final long serialVersionUID = 1L;
    /**
     * 代码点
     */
    int code;
    /**
     * 提示
     */
    @NotNull
    String msg;
    /**
     * 传输数据
     */
    @Nullable
    T data;

    private Dto() {
    }

    private Dto(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Dto<T> sc(T data) {
        return new Dto<T>(0, "Ok", data);
    }

    public static <T> Dto<T> err(int code, @NotNull String msg, @Nullable T data) {
        if (code <= 0) {
            throw new DtoInternalException(DtoInternalCode.INTERNAL_ERROR);
        }
        return new Dto<T>(code, msg, data);
    }

    public static <T> Dto<T> exp(DtoInternalCode code) {
        return new Dto<T>(code.getCode(), code.getMsg(), null);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public boolean ok() {
        return this.code == 0;
    }
}
