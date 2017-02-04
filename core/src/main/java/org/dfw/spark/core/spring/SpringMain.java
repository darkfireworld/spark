package org.dfw.spark.core.spring;

import org.dfw.spark.core.io.constant.Constant;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring主要配置，注意：这里的配置需要在具体启动器中引用
 */
@Configuration
@PropertySource(Constant.APPLICATION_PROPERTIES)
@ComponentScan(Constant.BASE_PACKAGE)
@EnableAspectJAutoProxy(proxyTargetClass = true)
// TODO 日志配置
public class SpringMain {

}