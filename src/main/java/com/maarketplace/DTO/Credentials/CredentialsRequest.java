package com.maarketplace.DTO.Credentials;

import com.maarketplace.model.Credentials;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsRequest {

    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 60, message = "Username must be between 3 and 60 characters.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
    private String password;

    public Credentials toEntity() {
        Credentials credentials = new Credentials();
        credentials.setUsername(this.username);
        credentials.setPassword(this.password); // en clair, sera crypté après
        return credentials;
    }

}
