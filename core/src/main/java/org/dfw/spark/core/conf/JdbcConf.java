package org.dfw.spark.core.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Jdbc 配置
 */
@EnableTransactionManagement
public abstract class JdbcConf {
    String jdbcClass;
    String jdbcUrl;
    String jdbcUsername;
    String jdbcPassword;

    /**
     * Jdbc配置
     *
     * @param jdbcClass    JDBC类名
     * @param jdbcUrl      连接地址
     * @param jdbcUsername 用户名
     * @param jdbcPassword 密码
     */
    public JdbcConf(String jdbcClass, String jdbcUrl, String jdbcUsername, String jdbcPassword) {
        this.jdbcClass = jdbcClass;
        this.jdbcUrl = jdbcUrl;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() throws Exception {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(jdbcClass);
        druidDataSource.setUrl(jdbcUrl);
        druidDataSource.setUsername(jdbcUsername);
        druidDataSource.setPassword(jdbcPassword);
        druidDataSource.setFilters("log4j");
        return druidDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
