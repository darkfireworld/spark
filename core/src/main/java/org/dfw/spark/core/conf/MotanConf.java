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
public abstract class MotanConf {
    // 心跳
    boolean heartbeat;
    // zk地址，例如：192.168.137.32:2181
    String zk;
    // 服务端口号
    int port;
    // 服务版本号
    String version;

    public MotanConf(boolean heartbeat, String zk, int port, String version) {
        this.heartbeat = heartbeat;
        this.zk = zk;
        this.port = port;
        this.version = version;
    }

    @Bean
    public ApplicationListener motanListener() {
        return new ApplicationListener<ContextRefreshedEvent>() {
            @Override
            public void onApplicationEvent(ContextRefreshedEvent event) {
                if (heartbeat) {
                    MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
                }
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
        config.setAddress(zk);
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
        config.setExport(String.format("motanProtocol:%s", String.valueOf(port)));
        config.setRegistry("motanRegistry");
        config.setVersion(version);
        return config;
    }

    @Bean
    public BasicRefererConfigBean motanBasicReferer() {
        BasicRefererConfigBean config = new BasicRefererConfigBean();
        config.setProtocol("motanProtocol");
        config.setRegistry("motanRegistry");
        config.setVersion(version);
        return config;
    }
}
