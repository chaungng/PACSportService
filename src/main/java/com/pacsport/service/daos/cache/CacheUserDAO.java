package com.pacsport.service.daos.cache;

import com.pacsport.service.daos.IUserDAO;
import com.pacsport.service.daos.data.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUserDAO implements IUserDAO {
    private final Map<String, User> users;
    private final Map<String, String> mapEmailId;

    public CacheUserDAO() {
        users = new ConcurrentHashMap<>();
        mapEmailId = new ConcurrentHashMap<>();
    }

    @Override
    public void set(final User user) {
        synchronized (this) {
            users.put(user.getId(), user);
            mapEmailId.put(user.getEmail(), user.getId());
        }
    }

    @Override
    public User get(final String id) {
        return users.get(id);
    }

    @Override
    public User getByEmail(final String email) {
        final String userId = mapEmailId.get(email);

        if (userId == null) {
            return null;
        }
        return users.get(userId);
    }
}
