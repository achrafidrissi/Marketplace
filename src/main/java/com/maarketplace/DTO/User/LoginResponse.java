package com.maarketplace.DTO.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String message;
    private String username;
    private UserResponse user;

    public LoginResponse(String message, String username, UserResponse user) {
        this.message = message;
        this.username = username;
        this.user = user;
    }
}
