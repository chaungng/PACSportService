package com.pacsport.service.models;

import com.pacsport.service.daos.IUserDAO;
import com.pacsport.service.daos.data.User;
import com.pacsport.service.exceptions.ExistedEmailException;
import com.pacsport.service.exceptions.InvalidAccessRequestException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Setter
@Getter
@Component
public class UserModel {

    @Autowired
    public IUserDAO userDAO;

    public String create(final String email,
                         final String password,
                         final String firstName,
                         final String lastName)
            throws ExistedEmailException {
        if (isEmailExisted(email)) {
            throw new ExistedEmailException(email);
        }
        final String userId = UUID.randomUUID().toString();
        userDAO.set(User.builder()
                .id(userId)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build());
        return userId;
    }

    public User get(String id) throws InvalidAccessRequestException {
        User user = userDAO.get(id);
        if (user == null) {
            throw new InvalidAccessRequestException("id not found");
        }
        return user;
    }

    public void update(String id,
                       final String firstName,
                       final String lastName,
                       final String birthday) throws InvalidAccessRequestException {
        User user = userDAO.get(id);
        if (user == null) {
            throw new InvalidAccessRequestException("id not found");
        }

        user.setFirstName(StringUtils.isEmpty(firstName) ? user.getFirstName() : firstName);
        user.setLastName(StringUtils.isEmpty(lastName) ? user.getLastName() : lastName);
        user.setBirthday(StringUtils.isEmpty(firstName) ? user.getBirthday() : birthday);
        userDAO.set(user);
    }


    private boolean isEmailExisted(final String email) {
        User user = userDAO.getByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}
