package org.dfw.spark.web.runner;

import org.dfw.spark.core.conf.ExecutorConf;
import org.dfw.spark.core.conf.LogbackConf;
import org.dfw.spark.core.conf.MotanConf;
import org.dfw.spark.core.conf.MvcConf;
import org.dfw.spark.core.kit.PropertiesKit;
import org.dfw.spark.core.runner.WebRunner;
import org.springframework.context.annotation.Configuration;

/**
 * 启动器
 */
@Configuration
public class WebEchoRunner {
    static public void main(String[] args) throws Exception {

        // 启动server
        new WebRunner().start(PropertiesKit.get("web.echo.server.context", "/"),
                Integer.valueOf(PropertiesKit.get("web.echo.server.port", "80")));
    }

    @Configuration
    public static class AppMotanConf extends MotanConf {

        public AppMotanConf() {
            super(true,
                    PropertiesKit.get("core.motan.zk"),
                    Integer.valueOf(PropertiesKit.get("web.echo.motan.port")),
                    PropertiesKit.get("web.echo.motan.version"));
        }
    }

    @Configuration
    public static class AppMvcConf extends MvcConf {

    }

    @Configuration
    public static class AppExecutorConf extends ExecutorConf {

    }

    @Configuration
    public static class AppLogbackConf extends LogbackConf {

        public AppLogbackConf() {
            super(PropertiesKit.get("web.echo.logback.dir"),
                    PropertiesKit.get("web.echo.logback.level"));
        }
    }
}
