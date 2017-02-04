package org.dfw.spark.service.echo;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import org.dfw.spark.api.echo.EchoService;
import org.dfw.spark.core.spring.SpringTest;
import org.dfw.spark.core.support.dto.Dto;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Administrator on 2017/2/4.
 */
public class EchoServiceImplTest extends SpringTest {
    @MotanReferer
    EchoService echoService;

    @Test
    public void echo() {
        Dto<String> dto = echoService.echo(UUID.randomUUID().toString());
        Assert.assertTrue(dto.ok());
    }
}
