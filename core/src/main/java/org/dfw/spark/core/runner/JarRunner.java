package org.dfw.spark.core.runner;

import org.dfw.spark.core.spring.SpringMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 启动器
 */
public class JarRunner {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void start() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringMain.class);
        logger.debug("start application : " + context.getId());
    }
}
