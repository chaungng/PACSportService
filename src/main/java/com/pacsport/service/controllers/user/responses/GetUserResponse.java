package com.pacsport.service.controllers.user.responses;

import com.pacsport.service.daos.data.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetUserResponse {
    private User user;
}
