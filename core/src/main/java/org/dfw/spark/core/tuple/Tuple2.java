package org.dfw.spark.core.tuple;

/**
 * Created by Administrator on 2015/4/24.
 */
public class Tuple2<T, K> {
    public T _1;
    public K _2;

    public Tuple2(T t, K k) {
        this._1 = t;
        this._2 = k;
    }

    public Tuple2() {
    }
}
