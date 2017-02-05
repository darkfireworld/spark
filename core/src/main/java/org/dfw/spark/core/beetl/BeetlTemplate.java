package org.dfw.spark.core.beetl;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

public class BeetlTemplate {

    GroupTemplate gt;

    /**
     * 初始化Beetl
     */
    public BeetlTemplate() {
        try {
            // 使用加载beetl的classloader
            ResourceLoader resourceLoader = new ClasspathResourceLoader(BeetlTemplate.class.getClassLoader());
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
