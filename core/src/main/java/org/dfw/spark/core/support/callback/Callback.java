package org.dfw.spark.core.support.callback;

/**
 * Created by Administrator on 2015/5/1.
 */
public interface Callback<T, K> {
    void onSuccess(T res);

    void onError(K error);
}
