package org.dfw.spark.core.support.kit;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/11/28.
 */
public class IoKit {
    /**
     * 静默-关闭Closeable对象
     *
     * @param closeable closeable对象
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            // NONE
        }
    }

    /**
     * 拷贝数据
     *
     * @param from FROM
     * @param to   TO
     * @return byte size
     */
    public static long copy(InputStream from, OutputStream to) throws IOException {
        checkNotNull(from);
        checkNotNull(to);
        byte[] buf = createBuffer();
        long total = 0;
        while (true) {
            int r = from.read(buf);
            if (r == -1) {
                break;
            }
            to.write(buf, 0, r);
            total += r;
        }
        return total;
    }

    private static byte[] createBuffer() {
        return new byte[8192];
    }

    private static <T> T checkNotNull(T reference) {

        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
