package org.dfw.spark.core.kit;

import org.dfw.spark.core.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Properties 读取器
 */
public class PropertiesKit {
    final static Logger logger = LoggerFactory.getLogger(PropertiesKit.class);
    final static Map<String, Properties> propertiesCacheMap = new ConcurrentHashMap<String, Properties>();


    /**
     * 读取一个指定的properties文件
     *
     * @param filename 文件名字
     */
    synchronized static private Properties load(String filename) {
        Properties properties = propertiesCacheMap.get(filename);
        if (properties == null) {
            properties = new Properties();
            propertiesCacheMap.put(filename, properties);
            // 读取数据
            try {
                ClassLoader cl = PropertiesKit.class.getClassLoader();
                Enumeration<URL> urlEnumeration = cl.getResources(filename);
                while (urlEnumeration.hasMoreElements()) {
                    URL url = urlEnumeration.nextElement();
                    InputStream is = null;
                    try {
                        is = url.openStream();
                        properties.load(is);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    } finally {
                        // close is
                        if (is != null) {
                            try {
                                is.close();
                            } catch (Exception e) {
                                // NONE
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return properties;
    }

    /**
     * 读取数值
     *
     * @param filename     文件名
     * @param key          KEY
     * @param defaultValue 默认属性值
     */
    public static String getBy(String filename, String key, String defaultValue) {
        Properties properties = load(filename);
        return properties.getProperty(key, defaultValue);
    }

    /**
     * 读取数值
     *
     * @param filename 文件名
     * @param key      KEY
     */
    public static String getBy(String filename, String key) {
        Properties properties = load(filename);
        return properties.getProperty(key);
    }

    /**
     * 读取数值
     *
     * @param key          KEY
     * @param defaultValue 默认值
     */
    public static String get(String key, String defaultValue) {
        Properties properties = load(Constant.APPLICATION_PROPERTIES);
        return properties.getProperty(key, defaultValue);
    }

    /**
     * 读取数值
     *
     * @param key KEY
     */
    public static String get(String key) {
        Properties properties = load(Constant.APPLICATION_PROPERTIES);
        return properties.getProperty(key);
    }
}
