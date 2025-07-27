package com.maarketplace.controller.validator;

import com.maarketplace.DTO.Credentials.CredentialsRequest;
import com.maarketplace.helpers.Utils;
import com.maarketplace.helpers.validators.FieldValidators;
import com.maarketplace.helpers.validators.TypeValidators;
import com.maarketplace.model.Credentials;
import com.maarketplace.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CredentialsValidator implements Validator {

    @Autowired
    private CredentialsRepository credentialsRepository;

    private String confirmPassword;
    private Boolean isAccountUpdate = false;
    private String currentUsername;

    public Boolean getIsAccountUpdate() {
        return this.isAccountUpdate;
    }

    public void setIsAccountUpdate(Boolean isAccountUpdate) {
        this.isAccountUpdate = isAccountUpdate;
    }

    public String getCurrentUsername() {
        return this.currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public void validate(@NonNull Object object, @NonNull Errors errors) {
        if (!(object instanceof CredentialsRequest credentialsRequest)) {
            throw new IllegalArgumentException("Expected CredentialsRequest object");
        }

        String username = credentialsRequest.getUsername();
        String password = credentialsRequest.getPassword();

        // Check username uniqueness
        if ((!isAccountUpdate && credentialsRepository.existsByUsername(username)) ||
                (isAccountUpdate && !currentUsername.equals(username) && credentialsRepository.existsByUsername(username))) {
            errors.rejectValue("credentials.username", "credentials.username.unique", "Username already used.");
        }

        // Check password format
        if ((!isAccountUpdate && !FieldValidators.passwordValidator(password)) ||
                (isAccountUpdate && TypeValidators.validateString(password) && !FieldValidators.passwordValidator(password))) {
            errors.rejectValue("credentials.password", "credentials.password.invalidFormat",
                    "The password must be 8 characters long and must have uppercase, lowercase and numbers.");
        }

        // Check password confirmation
        if (confirmPassword == null || !password.equals(confirmPassword)) {
            errors.rejectValue("credentials.password", "credentials.password.passwordDifferentFromConfirmPasswordError",
                    "The confirm-password must be the same as the password.");
        }
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return CredentialsRequest.class.equals(clazz);
    }
}
