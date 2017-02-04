package org.dfw.spark.core.support.callback;

/**
 * Created by Administrator on 2015/6/10.
 */
public interface PECallback<T, K> {
    void onSuccess(T res);

    void onError(K error);

    void onProgress(int progress);

    void onThrowable(Throwable e);
}
