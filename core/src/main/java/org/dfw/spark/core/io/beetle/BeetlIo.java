package org.dfw.spark.core.io.beetle;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

public class BeetlIo {

    GroupTemplate gt;

    /**
     * 初始化Beetl
     */
    public BeetlIo() {
        try {
            // 使用加载beetl的classloader
            ResourceLoader resourceLoader = new ClasspathResourceLoader(BeetlIo.class.getClassLoader());
            Configuration cfg = Configuration.defaultConfiguration();
            gt = new GroupTemplate(resourceLoader, cfg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取一个模版
     */
    public Template getTemplate(String tpl) {
        return gt.getTemplate(tpl);
    }
}
