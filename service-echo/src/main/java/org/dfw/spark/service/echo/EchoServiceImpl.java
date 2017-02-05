package org.dfw.spark.service.echo;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import org.dfw.spark.api.echo.EchoService;
import org.dfw.spark.core.dto.Dto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Echo
 */
@MotanService
public class EchoServiceImpl implements EchoService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Dto<String> echo(String name) {
        logger.debug("RECV：" + name);
        return Dto.sc("Hello：" + name);
    }
}
