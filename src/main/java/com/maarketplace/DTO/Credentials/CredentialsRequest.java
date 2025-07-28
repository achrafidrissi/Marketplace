package com.maarketplace.DTO.Credentials;

import com.maarketplace.model.Credentials;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "IdentifiantsConnexion", description = "Contient les identifiants pour l’authentification d’un utilisateur.")
public class CredentialsRequest {

    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 60, message = "Username must be between 3 and 60 characters.")
    @Schema(description = "Nom d'utilisateur ou email", example = "utilisateur@bam.ma")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
    @Schema(description = "Mot de passe en clair", example = "MonMotDePasse123")
    private String password;

    public Credentials toEntity() {
        Credentials credentials = new Credentials();
        credentials.setUsername(this.username);
        credentials.setPassword(this.password); // en clair, sera crypté après
        return credentials;
    }

}
