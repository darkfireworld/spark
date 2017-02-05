package org.dfw.spark.core.kit;

import org.dfw.spark.core.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/12/4.
 */
public class TimeoutKit {
    static Logger logger = LoggerFactory.getLogger(TimeoutKit.class);

    /**
     * 超时检测调用工具
     *
     * @param bizName 业务名字
     * @param timeout 超时时限（毫秒），如果小于等于0，则表示忽略检测
     * @param call    调用执行器
     * @param param   参数
     */
    @Nullable
    static public <P, R> R timeout(String bizName, long timeout, Call<P, R> call, @Nullable P param) {
        long startTime = System.currentTimeMillis();
        try {
            return call.apply(param);
        } finally {
            long endTime = System.currentTimeMillis();
            if (timeout > 0 && (endTime - startTime) >= timeout) {
                logger.error("Timeout:{}Ms BizName:{}", endTime - startTime, bizName);
            }
        }
    }

    public interface Call<P, R> {
        @Nullable
        R apply(@Nullable P param);
    }
}
