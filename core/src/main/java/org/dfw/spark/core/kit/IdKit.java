package org.dfw.spark.core.kit;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/12.
 */
public class IdKit {
    static Snowflake snowflake = new Snowflake(0, 0);
    static Siri siri = new Siri(0);

    /**
     * 获取一个UUID
     *
     * @return UUID
     */
    public static String uuid() {
        return String.valueOf(snowflake.nextId());
    }

    /**
     * 获取一个格式为 201612121230120001 格式的ID
     *
     * @return UUID
     */
    public static String uuid2() {
        return String.valueOf(siri.nextId());
    }

    /**
     * 每毫秒生成一个UUID，生成的格式为 yyyyMMddHHmmssSSS + workId + 0001
     */
    public static class Siri {
        long workerId = 0L;
        long lastTimestamp = 0L;

        public Siri(long workerId) {
            this.workerId = workerId;
        }

        /**
         * 生成下一个UUID
         */
        public synchronized long nextId() {
            while (true) {
                long now = System.currentTimeMillis();
                if (lastTimestamp == now) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // 更新
                    lastTimestamp = now;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    return Long.parseLong(String.format("%s%d%03d", sdf.format(new Date(now)), workerId, now % 1000));
                }
            }

        }
    }

    /**
     * Snowflake 算法
     */
    public static class Snowflake {
        //static final Logger LOG = LoggerFactory.getLogger(IdWorker.class);

        long workerId;
        long datacenterId;
        long sequence = 0L;

        long twepoch = 1288834974657L;

        long workerIdBits = 5L;
        long datacenterIdBits = 5L;
        long maxWorkerId = -1L ^ (-1L << workerIdBits);
        long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
        long sequenceBits = 12L;

        long workerIdShift = sequenceBits;
        long datacenterIdShift = sequenceBits + workerIdBits;
        long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        long sequenceMask = -1L ^ (-1L << sequenceBits);

        private long lastTimestamp = -1L;

        public Snowflake(long workerId, long datacenterId) {
            // sanity check for workerId
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
            }
            if (datacenterId > maxDatacenterId || datacenterId < 0) {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
            }
            this.workerId = workerId;
            this.datacenterId = datacenterId;
            //LOG.info(String.format("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d", timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId));
        }

        public synchronized long nextId() {
            long timestamp = timeGen();

            if (timestamp < lastTimestamp) {
                //LOG.error(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            }

            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask;
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0L;
            }

            lastTimestamp = timestamp;

            return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
        }

        protected long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        protected long timeGen() {
            return System.currentTimeMillis();
        }
    }
}