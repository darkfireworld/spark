package org.dfw.spark.web.runner;

import org.dfw.spark.core.conf.ExecutorConf;
import org.dfw.spark.core.conf.MotanConf;
import org.dfw.spark.core.conf.MvcConf;
import org.dfw.spark.core.runner.WebRunner;
import org.springframework.context.annotation.Configuration;

/**
 * 启动器
 */
@Configuration
public class WebEchoRunner {
    static public void main(String[] args) throws Exception {
        new WebRunner().start("/", 8080);
    }

    @Configuration
    public static class AppMotanConf extends MotanConf {

        public AppMotanConf() {
            super(true, "192.168.137.32:2181", 8020, "1.0.0");
        }
    }

    @Configuration
    public static class AppMvcConf extends MvcConf {

    }

    @Configuration
    public static class AppExecutorConf extends ExecutorConf {

    }
}
