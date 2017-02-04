package org.dfw.spark.core.support.kit;

/**
 * Created by Administrator on 2016/12/8.
 */
public class FloatKit {
    /**
     * 精确到小数后两位
     *
     * @param val 数值
     * @return 返回精确后的字符串
     */
    public static String correct2(float val) {
        return String.format("%.2f", val);
    }

    /**
     * 精确到小数后两位
     *
     * @param val 数值
     * @return 返回精确后的字符串
     */
    public static String correct2(double val) {
        return String.format("%.2f", val);
    }
}
