package com.pacsport.service.daos;

import com.pacsport.service.daos.data.User;

public interface IUserDAO {
    void set(final User user);
    User get(final String id);
    User getByEmail(final String email);
}
