package org.dfw.spark.core.conf;

import org.dfw.spark.core.io.beetle.BeetlIo;
import org.dfw.spark.core.io.http.HttpIo;
import org.dfw.spark.core.io.poi.PoiIo;
import org.springframework.context.annotation.Bean;

/**
 * 通用工具配置
 */
public class IoConf {
    @Bean
    public BeetlIo beetlIo() {
        return new BeetlIo();
    }

    @Bean
    public HttpIo httpIo() {
        return new HttpIo();
    }

    @Bean
    public PoiIo poiIo() {
        return new PoiIo();
    }
}
