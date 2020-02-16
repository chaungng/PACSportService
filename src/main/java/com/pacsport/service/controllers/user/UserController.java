package com.pacsport.service.controllers.user;

import com.pacsport.service.controllers.ErrorResponse;
import com.pacsport.service.controllers.PACSportController;
import com.pacsport.service.controllers.user.requests.CreateUserRequest;
import com.pacsport.service.controllers.user.requests.LoginRequest;
import com.pacsport.service.controllers.user.requests.UpdateUserRequest;
import com.pacsport.service.controllers.user.responses.CreateUserResponse;
import com.pacsport.service.controllers.user.responses.GetUserResponse;
import com.pacsport.service.controllers.user.responses.LoginResponse;
import com.pacsport.service.daos.data.User;
import com.pacsport.service.daos.data.UserSession;
import com.pacsport.service.exceptions.ExistedEmailException;
import com.pacsport.service.exceptions.InvalidAccessRequestException;
import com.pacsport.service.models.LoginModel;
import com.pacsport.service.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends PACSportController {

    @Autowired
    UserModel userModel;
    @Autowired
    LoginModel loginModel;

    @PostMapping("/user")
    public ResponseEntity<?> create(@RequestBody CreateUserRequest request) {
        try {
            final String userId = userModel.create(request.getEmail(),
                    request.getPassword(),
                    request.getFirstName(),
                    request.getLastName());
            CreateUserResponse createUserResponse = CreateUserResponse.builder()
                    .id(userId)
                    .build();

            return new ResponseEntity<>(createUserResponse, HttpStatus.OK);
        } catch (ExistedEmailException e) {
            return new ResponseEntity<>(
                    ErrorResponse.builder().message(e.getMessage()).build(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id, @RequestHeader HttpHeaders headers) {
        try {
            loginModel.validate(id, headers.get(HttpHeaders.AUTHORIZATION).get(1));
            User user = userModel.get(id);
            return new ResponseEntity<>(
                    GetUserResponse.builder().user(user).build(),
                    HttpStatus.OK);
        } catch (InvalidAccessRequestException e) {
            return new ResponseEntity<>(
                    ErrorResponse.builder().message(e.getMessage()).build(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
                                    @RequestBody UpdateUserRequest request,
                                    @RequestHeader HttpHeaders headers) {
        try {
            loginModel.validate(id, headers.get(HttpHeaders.AUTHORIZATION).get(1));
            userModel.update(
                    id,
                    request.getFirstName(),
                    request.getLastName(),
                    request.getBirthday());
            return new ResponseEntity<>(
                    HttpStatus.OK.name(),
                    HttpStatus.OK);
        } catch (InvalidAccessRequestException e) {
            return new ResponseEntity<>(
                    ErrorResponse.builder().message(e.getMessage()).build(),
                    HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            UserSession session = loginModel.login(request.getEmail(),
                    request.getPassword(),
                    request.isRemember());
            return new ResponseEntity<>(
                    LoginResponse.builder()
                            .sessionToken(session.getSessionToken())
                            .expireInMilliSeconds(session.getExpireInMilliSeconds())
                            .build(),
                    HttpStatus.OK);
        } catch (InvalidAccessRequestException e) {
            return new ResponseEntity<>(
                    ErrorResponse.builder().message(e.getMessage()).build(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
