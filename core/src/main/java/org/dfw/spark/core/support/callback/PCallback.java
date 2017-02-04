package org.dfw.spark.core.support.callback;

/**
 * Created by Administrator on 2015/5/5.
 */
public interface PCallback<T, K> {
    void onSuccess(T res);

    void onError(K error);

    void onProgress(int progress);
}
