package org.dfw.spark.core.conf;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.config.springsupport.*;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Motan 配置
 */
// TODO 配置
public class MotanConf {

    @Bean
    public ApplicationListener motanListener() {
        return new ApplicationListener<ContextRefreshedEvent>() {
            @Override
            public void onApplicationEvent(ContextRefreshedEvent event) {
                MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
            }
        };
    }

    @Bean
    public AnnotationBean motanAnnotationBean() {

        return new AnnotationBean();
    }

    @Bean
    public RegistryConfigBean motanRegistry() {
        RegistryConfigBean config = new RegistryConfigBean();
        config.setRegProtocol("zookeeper");
        config.setAddress("192.168.137.32:2181");
        return config;
    }

    @Bean
    public ProtocolConfigBean motanProtocol() {
        ProtocolConfigBean protocolConfigBean = new ProtocolConfigBean();
        protocolConfigBean.setName("motan");
        return protocolConfigBean;
    }

    @Bean
    public BasicServiceConfigBean baseServiceConfig() {
        BasicServiceConfigBean config = new BasicServiceConfigBean();
        config.setShareChannel(true);
        config.setExport("motanProtocol:8082");
        config.setRegistry("motanRegistry");
        config.setVersion("1.0.0");
        return config;
    }

    @Bean
    public BasicRefererConfigBean motanBasicReferer() {
        BasicRefererConfigBean config = new BasicRefererConfigBean();
        config.setProtocol("motanProtocol");
        config.setRegistry("motanRegistry");
        config.setVersion("1.0.0");
        return config;
    }
}
