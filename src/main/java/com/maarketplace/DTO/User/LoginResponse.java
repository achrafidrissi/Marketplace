package com.maarketplace.DTO.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "ConnexionRéponse", description = "Réponse renvoyée après une authentification réussie.")
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
