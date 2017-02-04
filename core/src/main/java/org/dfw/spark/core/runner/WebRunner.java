package org.dfw.spark.core.runner;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ErrorPage;
import org.dfw.spark.core.spring.SpringMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;

/**
 * 启动器
 */
public class WebRunner {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 启动web
     *
     * @param context 上下文地址
     * @param port    端口号
     */
    public void start(String context,int port) throws Exception {
        try {

            // web app config
            DeploymentInfo servletBuilder = Servlets.deployment()
                    .setClassLoader(WebRunner.class.getClassLoader())
                    .setContextPath(context)
                    .setDeploymentName("Undertow")
                    // filter
                    .addFilters(Servlets.filter("encodingFilter", CharacterEncodingFilter.class)
                            .addInitParam("encoding", "UTF-8")
                            .addInitParam("forceEncoding", "true")
                    )
                    .addFilterUrlMapping("encodingFilter", "/*", DispatcherType.REQUEST)
                    // Servlet
                    .addServlets(
                            Servlets.servlet("DispatcherServlet", DispatcherServlet.class)
                                    .addInitParam("contextClass", AnnotationConfigWebApplicationContext.class.getName())
                                    .addInitParam("contextConfigLocation", SpringMain.class.getName())
                                    .addMapping("/*")
                    )
                    // 主页
                    .addWelcomePage("/index.html")
                    // 错误页面
                    .addErrorPage(new ErrorPage("/404.html", 404))
                    .addErrorPage(new ErrorPage("/500.html", 500))
                    .addErrorPage(new ErrorPage("/exception.html", Exception.class));
            DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
            manager.deploy();
            // config path
            HttpHandler servletHandler = manager.start();
            PathHandler path = Handlers.path(Handlers.redirect(context)).addPrefixPath(context, servletHandler);
            // undertow server
            Undertow server = Undertow.builder()
                    .addHttpListener(port, "0.0.0.0")
                    .setHandler(path)
                    .build();
            server.start();
        } catch (ServletException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
