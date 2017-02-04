package org.dfw.spark.web.runner;

import org.dfw.spark.core.conf.ExecutorConf;
import org.dfw.spark.core.conf.IoConf;
import org.dfw.spark.core.conf.MotanConf;
import org.dfw.spark.core.conf.MvcConf;
import org.dfw.spark.core.runner.WebRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 启动器
 */
@Configuration
@Import({ExecutorConf.class, IoConf.class, MvcConf.class, MotanConf.class})
public class WebEchoRunner {
    static public void main(String[] args) throws Exception {
        new WebRunner().start("/", 8080);
    }
}
