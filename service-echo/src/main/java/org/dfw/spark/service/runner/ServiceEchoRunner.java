package org.dfw.spark.service.runner;

import org.dfw.spark.core.conf.MotanConf;
import org.dfw.spark.core.runner.JarRunner;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceEchoRunner {
    static public void main(String[] args) {
        new JarRunner().start();
    }

    @Configuration
    public static class AppMotanConf extends MotanConf {

        public AppMotanConf() {
            super(true, "192.168.137.32:2181", 8020, "1.0.0");
        }
    }
}
