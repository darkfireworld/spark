package org.dfw.spark.core.support.callback;

/**
 * 单回调
 */
public interface SCallback<T> {
    void onOk(T t);
}
