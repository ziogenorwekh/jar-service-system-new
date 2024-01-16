package com.jar.service.system.database.service.jpa.config;

import javax.sql.DataSource;

import com.jar.service.system.database.service.jpa.data.JpaDatabaseConfigData;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class JpaDatabaseConfig {

    private final JpaDatabaseConfigData jpaDatabaseConfigData;

    public JpaDatabaseConfig(JpaDatabaseConfigData jpaDatabaseConfigData) {
        this.jpaDatabaseConfigData = jpaDatabaseConfigData;
    }

    @Primary
    @Bean(name = "JpaDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .password(jpaDatabaseConfigData.getPassword())
                .url(jpaDatabaseConfigData.getDatabaseUrl())
                .username(jpaDatabaseConfigData.getUsername())
                .driverClassName(jpaDatabaseConfigData.getDriverClassName())
                .build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("JpaDataSource") DataSource dataSource) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", jpaDatabaseConfigData.getHibernateAuto());
        properties.put("hibernate.show_sql", jpaDatabaseConfigData.getShowSql());
        return builder
                .dataSource(dataSource)
                .packages(jpaDatabaseConfigData.getPackageScanArea()) // Update with your domain package
                .persistenceUnit(jpaDatabaseConfigData.getPersistenceUnit())
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
