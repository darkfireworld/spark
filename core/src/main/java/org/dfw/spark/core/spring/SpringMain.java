package org.dfw.spark.core.spring;

import org.dfw.spark.core.constant.Constant;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Spring主要配置，注意：这里的配置需要在具体启动器中引用
 */
@Configuration
@ComponentScan(Constant.BASE_PACKAGE)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringMain {

}