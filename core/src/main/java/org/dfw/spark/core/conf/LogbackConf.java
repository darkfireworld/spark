package org.dfw.spark.core.conf;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import com.google.common.base.Strings;
import org.dfw.spark.core.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 日志配置
 */
public abstract class LogbackConf implements InitializingBean {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    // 日志输入文件夹地址，可空
    @Nullable
    String logbackDir;
    // 日志输入等级，可空
    @Nullable
    String logbackLevel;

    public LogbackConf(String logbackDir, String logbackLevel) {
        this.logbackDir = Strings.nullToEmpty(logbackDir).trim();
        this.logbackLevel = Strings.nullToEmpty(logbackLevel).trim();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
            // 重置日志上下文
            context.reset();
            // 输出目录设置
            if (!Strings.isNullOrEmpty(logbackDir)) {
                context.putProperty("logback-dir", logbackDir);
            }
            // 日志级别设置
            if (!Strings.isNullOrEmpty(logbackLevel)) {
                context.putProperty("logback-level", logbackLevel);
            }
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            // 重新配置日志
            configurator.doConfigure(LogbackConf.class.getResourceAsStream("/logback.xml"));
            logger.debug("logback done");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
