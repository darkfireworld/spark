package org.dfw.spark.service.runner;

import org.dfw.spark.core.conf.ExecutorConf;
import org.dfw.spark.core.conf.MotanConf;
import org.dfw.spark.core.runner.JarRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({ExecutorConf.class, MotanConf.class})
public class ServiceEchoRunner {
    static public void main(String[] args) {
        new JarRunner().start();
    }
}
