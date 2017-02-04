package org.dfw.spark.core.support.kit;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/1/13.
 */
public class ThreadPoolKit {
    /**
     * 创建一个线程池
     *
     * @param coreThreadSize  核心线程数量
     * @param maxThreadSize   最多线程数量
     * @param keepAliveTime   保持时间
     * @param unit            时间单位
     * @param groupThreadName 线程组的名称
     */
    public static ThreadPoolExecutor createThreadPoolExecutor(int coreThreadSize,
                                                              int maxThreadSize,
                                                              long keepAliveTime,
                                                              TimeUnit unit,
                                                              final String groupThreadName) {
        return new ThreadPoolExecutor(coreThreadSize, maxThreadSize, keepAliveTime,
                unit, new LinkedBlockingQueue<Runnable>(),
                new ThreadFactory() {
                    AtomicInteger count = new AtomicInteger(1);

                    ThreadGroup group;

                    {
                        SecurityManager s = System.getSecurityManager();
                        group = (s != null) ? s.getThreadGroup() :
                                Thread.currentThread().getThreadGroup();
                    }

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(group, r, String.format("%s-%d", groupThreadName, count.getAndIncrement()), 0);
                        if (t.isDaemon())
                            t.setDaemon(false);
                        if (t.getPriority() != Thread.NORM_PRIORITY)
                            t.setPriority(Thread.NORM_PRIORITY);
                        return t;
                    }
                });
    }

    /**
     * 创建一个线程池
     *
     * @param coreThreadSize  核心线程数量
     * @param groupThreadName 线程组的名称
     */
    public static ScheduledExecutorService createScheduledExecutorService(int coreThreadSize,
                                                                          final String groupThreadName) {
        return Executors.newScheduledThreadPool(coreThreadSize, new ThreadFactory() {
            AtomicInteger count = new AtomicInteger(1);

            ThreadGroup group;

            {
                SecurityManager s = System.getSecurityManager();
                group = (s != null) ? s.getThreadGroup() :
                        Thread.currentThread().getThreadGroup();
            }

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(group, r, String.format("%s-%d", groupThreadName, count.getAndIncrement()), 0);
                if (t.isDaemon())
                    t.setDaemon(false);
                if (t.getPriority() != Thread.NORM_PRIORITY)
                    t.setPriority(Thread.NORM_PRIORITY);
                return t;
            }
        });
    }
}
