package com.pacsport.service.daos.cache;

import com.pacsport.service.daos.IUserSessionDAO;
import com.pacsport.service.daos.data.UserSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUserSessionDAO implements IUserSessionDAO {
    private final Map<String, UserSession> sessions;

    public CacheUserSessionDAO() {
        sessions = new ConcurrentHashMap<>();
    }

    @Override
    public void add(final UserSession session) {
        synchronized (this) {
            sessions.putIfAbsent(
                    buildSessionId(session.getUserId(), session.getSessionToken()),
                    session
            );
        }
    }

    @Override
    public UserSession get(final String id, final String sessionToken) {
        return sessions.get(buildSessionId(id, sessionToken));
    }

    private String buildSessionId(String id,
                                  String sessionToken) {
        return String.format("%s.%s", id, sessionToken);
    }
}
