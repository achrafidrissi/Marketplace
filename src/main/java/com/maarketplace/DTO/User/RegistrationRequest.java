package com.maarketplace.DTO.User;

import com.maarketplace.DTO.Credentials.CredentialsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class RegistrationRequest {

    @Valid
    private UserRequest user;

    @Valid
    private CredentialsRequest credentials;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    // ✅ Getters & setters
    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }

    public CredentialsRequest getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsRequest credentials) {
        this.credentials = credentials;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
