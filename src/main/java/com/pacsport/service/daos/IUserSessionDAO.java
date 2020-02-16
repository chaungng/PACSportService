package com.pacsport.service.daos;

import com.pacsport.service.daos.data.UserSession;

public interface IUserSessionDAO {
    void add(UserSession session);

    UserSession get(String id, String sessionToken);
}
