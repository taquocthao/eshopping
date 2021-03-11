package com.tathao.eshopping.config;


import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:datasource-cfg.properties")
public class DataSourceInitializer {


    @Autowired
    private Environment env;

    @Bean(name="dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
        dataSource.setUrl(env.getProperty("ds.url"));
        dataSource.setUsername(env.getProperty("ds.username"));
        dataSource.setPassword(env.getProperty("ds.password"));
        dataSource.setSchema(env.getProperty("ds.schema"));

        return dataSource;
    }

    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory() {
        System.out.println("GET SESSION FACTORY");
        try {
            Properties properties = new  Properties();

            properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
            properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
            properties.put("current_session_context_class", env.getProperty("current_session_context_class"));

            LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

            factoryBean.setPackagesToScan(new String[] {"com.tathao.eshopping.model.entity"});
            factoryBean.setDataSource(getDataSource());
            factoryBean.setHibernateProperties(properties);
            factoryBean.afterPropertiesSet();

            SessionFactory factory = factoryBean.getObject();
            System.out.println("GET SESSION FACTORY SUCCESS "+ factory);
            return factory;

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(getSessionFactory());

        return transactionManager;
    }

}
