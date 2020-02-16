package com.pacsport.service.daos.mysql;

import com.pacsport.service.daos.IUserDAO;
import com.pacsport.service.daos.data.User;

public class MySQLUserDAO implements IUserDAO {
    @Override
    public void set(User user) {
        
    }

    @Override
    public User get(String id) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }
}
