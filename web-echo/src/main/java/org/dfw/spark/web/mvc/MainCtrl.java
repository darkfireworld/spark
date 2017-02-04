package org.dfw.spark.web.mvc;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import org.dfw.spark.api.echo.EchoService;
import org.dfw.spark.core.support.dto.Dto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/MainCtrl/")
public class MainCtrl {
    @MotanReferer
    EchoService echoService;

    @RequestMapping("/echo.do")
    public Dto<String> echo(@RequestParam("name") String name) {
        return echoService.echo(name);
    }
}
