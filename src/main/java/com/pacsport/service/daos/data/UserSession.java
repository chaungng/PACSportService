package com.pacsport.service.daos.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {
    private String userId;
    private String sessionToken;
    private long expireInMilliSeconds;
}
