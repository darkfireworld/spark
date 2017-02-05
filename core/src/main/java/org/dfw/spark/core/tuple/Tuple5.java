package org.dfw.spark.core.tuple;

/**
 * Created by Administrator on 2015/11/4.
 */
public class Tuple5<M, T, K, V, Z> {
    public M _1;
    public T _2;
    public K _3;
    public V _4;
    public Z _5;

    public Tuple5() {
    }

    public Tuple5(M _1, T _2, K _3, V _4, Z _5) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
    }
}
