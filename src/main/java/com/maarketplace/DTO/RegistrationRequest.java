package com.maarketplace.DTO;

import com.maarketplace.model.Credentials;
import com.maarketplace.model.User;
import jakarta.validation.Valid;

public class RegistrationRequest {

    @Valid
    private User user;

    @Valid
    private Credentials credentials;

    private String confirmPassword;

    // Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return credentials != null ? credentials.getPassword() : null;
    }
}
