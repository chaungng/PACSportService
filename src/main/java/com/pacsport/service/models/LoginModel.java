package com.pacsport.service.models;

import com.pacsport.service.daos.IUserDAO;
import com.pacsport.service.daos.IUserSessionDAO;
import com.pacsport.service.daos.data.User;
import com.pacsport.service.daos.data.UserSession;
import com.pacsport.service.exceptions.InvalidAccessRequestException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Setter
@Getter
@Component
public class LoginModel {

    @Autowired
    public IUserDAO userDAO;

    @Autowired
    public IUserSessionDAO userSessionDAO;

    public UserSession login(final String email,
                             final String password,
                             final boolean isRemembered) throws InvalidAccessRequestException {
        User user = userDAO.getByEmail(email);

        if (user == null
                || password != user.getPassword()) {
            throw new InvalidAccessRequestException("account not found or password mismatches");
        }

        String sessionToken = UUID.randomUUID().toString();

        final UserSession session = UserSession.builder()
                .sessionToken(sessionToken)
                .userId(user.getId())
                .expireInMilliSeconds(isRemembered ?
                        Long.MAX_VALUE : System.currentTimeMillis() + 86400000)
                .build();

        userSessionDAO.add(session);
        return session;
    }

    public void validate(String id, String sessionToken) throws InvalidAccessRequestException {
        UserSession session = userSessionDAO.get(id, sessionToken);

        if (session == null) {
            throw new InvalidAccessRequestException("not login account");
        }

        if (System.currentTimeMillis() > session.getExpireInMilliSeconds()) {
            throw new InvalidAccessRequestException("login session timeout");
        }
    }
}
