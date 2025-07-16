package com.maarketplace.controller.validator;

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
        Credentials credentials = (Credentials) object;
        if ((!this.getIsAccountUpdate() && this.credentialsRepository.existsByUsername(credentials.getUsername())) ||
                (this.getIsAccountUpdate() && !this.getCurrentUsername().equals(credentials.getUsername()) && this.credentialsRepository.existsByUsername(credentials.getUsername()))
        ) {
            errors.rejectValue("username", "credentials.username.unique", "Username already used.");
        }
        if ((!this.getIsAccountUpdate() && !FieldValidators.passwordValidator(credentials.getPassword())) || (this.isAccountUpdate && TypeValidators.validateString(credentials.getPassword()) && !FieldValidators.passwordValidator(credentials.getPassword()))
        ) {
            errors.rejectValue("password", "credentials.password.invalidFormat", "The password must be 8 characters long and must has uppercase, lowercase and numbers.");
        }
        if (this.getConfirmPassword() == null || !credentials.getPassword().equals(this.getConfirmPassword())) {
            errors.rejectValue("password", "credentials.password.passwordDifferentFromConfirmPasswordError", "The confirm-password must be the same as the password.");
        }

    }

    @Override
    public boolean supports(@NonNull Class<?> aClass) {
        return Credentials.class.equals(aClass);
    }
}

