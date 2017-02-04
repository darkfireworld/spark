package org.dfw.spark.core.support.kit;

import java.security.MessageDigest;

/**
 * 散列工具
 */
public class HashKit {
    /**
     * 转换为md5字符串
     *
     * @param text 文本
     * @return 经过md5计算后的文本
     */
    static public String md5(String text) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] rawData = text.getBytes("UTF-8");
            byte[] md5Data = md5.digest(rawData);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < md5Data.length; i++) {
                int val = ((int) md5Data[i]) & 0xff;
                if (val < 16)
                    sb.append("0");
                sb.append(Integer.toHexString(val));
            }
            return sb.toString().toLowerCase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
