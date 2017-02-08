package org.dfw.spark.service.runner;

import org.dfw.spark.core.conf.LogbackConf;
import org.dfw.spark.core.conf.MotanConf;
import org.dfw.spark.core.kit.PropertiesKit;
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
            super(true,
                    PropertiesKit.get("core.motan.zk"),
                    Integer.valueOf(PropertiesKit.get("service.echo.motan.port")),
                    PropertiesKit.get("service.echo.motan.version"));
        }
    }

    @Configuration
    public static class AppLogbackConf extends LogbackConf {

        public AppLogbackConf() {
            super(PropertiesKit.get("service.echo.logback.dir"),
                    PropertiesKit.get("service.echo.logback.level"));
        }
    }
}
