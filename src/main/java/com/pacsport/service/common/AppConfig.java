package com.pacsport.service.common;

import com.pacsport.service.daos.IUserDAO;
import com.pacsport.service.daos.IUserSessionDAO;
import com.pacsport.service.daos.cache.CacheUserSessionDAO;
import com.pacsport.service.daos.mysql.MySQLUserDAO;
import com.pacsport.service.models.LoginModel;
import com.pacsport.service.models.UserModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com.pacsport.service")
public class AppConfig {
    // DAO Beans initialization
    @Bean
    public IUserDAO getUserDAO() {
        return new MySQLUserDAO(getDataSource());
    }



    @Bean
    public DriverManagerDataSource getDataSource() {

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://bintech-inc.com:3306/pacsport");
        ds.setUsername("pacsport-admin");
        ds.setPassword("pacsport");

        return ds;
    }

    @Bean
    public IUserSessionDAO getUserSessionDAO() {
        return new CacheUserSessionDAO();
    }


    // Model Beans initialization
    @Bean
    public UserModel getUserModel() {
        return new UserModel();
    }

    @Bean
    public LoginModel getLoginModel() {
        return new LoginModel();
    }
}
