package com.maarketplace.DTO.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Connexion", description = "Requête de connexion d’un utilisateur.")
public class LoginRequest {

    @NotBlank(message = "Username is required")
    @Schema(description = "Username de connexion", example = "client12")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "Mot de passe", example = "MotDePasse123")
    private String password;
}
