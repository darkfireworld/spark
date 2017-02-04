package org.dfw.spark.core.conf;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.dfw.spark.core.support.dto.Dto;
import org.dfw.spark.core.support.dto.DtoInternalCode;
import org.dfw.spark.core.support.dto.DtoInternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Spring MVC 配置
 */
@EnableWebMvc
public class MvcConf extends WebMvcConfigurerAdapter {
    // 上传组件
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(1024 * 1024 * 32);
        return commonsMultipartResolver;
    }

    // 参数解析器
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // none
    }

    // MessageConverter Support for @RequestBody, etc
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 添加JSON支持
        converters.add(new FastJsonHttpMessageConverter());
    }

    // 默认Servlet支持
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // 异常处理器
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new AbstractHandlerExceptionResolver() {
            Logger logger = LoggerFactory.getLogger(this.getClass());

            // Fastjson视图解析器
            FastJsonJsonView fastJsonJsonView;

            {
                fastJsonJsonView = new FastJsonJsonView();
                fastJsonJsonView.setExtractValueFromSingleKeyModel(true);
            }

            @Override
            protected ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
                ModelAndView mv = new ModelAndView();
                if (e instanceof DtoInternalException) {
                    DtoInternalException exception = (DtoInternalException) e;
                    mv.addObject(Dto.exp(exception.getDtoInternalCode()));
                } else {
                    logger.error(e.getMessage(), e);
                    mv.addObject(Dto.exp(DtoInternalCode.INTERNAL_ERROR));
                }
                // 使用JSON解析器
                mv.setView(fastJsonJsonView);
                return mv;
            }
        });
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // index
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    /**
     * 添加static资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // survey
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
