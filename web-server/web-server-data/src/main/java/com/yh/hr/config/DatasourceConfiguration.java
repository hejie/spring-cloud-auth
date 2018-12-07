package com.yh.hr.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.sql.SQLException;

@Configuration
public class DatasourceConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DatasourceConfiguration.class);

    @Primary
    @Bean(name = "mysqlDS", initMethod = "init", destroyMethod = "close")
    @Qualifier("mysqlDS")
    @ConfigurationProperties(prefix = "datasource.mysql")
    public DruidDataSource dataSource1() throws SQLException {
        log.info("-------------------- mysqlDataSource init ---------------------");
        DruidDataSource druidDataSource = DataSourceBuilder.create().type(DruidDataSource.class).build();
        druidDataSource.setFilters("slf4j");

        return druidDataSource;
    }

}