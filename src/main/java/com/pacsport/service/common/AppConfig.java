package com.pacsport.service.common;

import com.pacsport.service.daos.IUserDAO;
import com.pacsport.service.daos.IUserSessionDAO;
import com.pacsport.service.daos.cache.CacheUserDAO;
import com.pacsport.service.daos.cache.CacheUserSessionDAO;
import com.pacsport.service.models.LoginModel;
import com.pacsport.service.models.UserModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.pacsport.service")
public class AppConfig {
    // DAO Beans initialization
    @Bean
    public IUserDAO getUserDAO() {
        return new CacheUserDAO();
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
