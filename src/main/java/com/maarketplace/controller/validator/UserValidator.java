package com.maarketplace.controller.validator;

import com.maarketplace.DTO.User.UserRequest;
import com.maarketplace.model.User;
import com.maarketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    private Boolean isAccountUpdate = false;

    public Boolean getIsAccountUpdate() {
        return this.isAccountUpdate;
    }

    public void setIsAccountUpdate(Boolean isAccountUpdate) {
        this.isAccountUpdate = isAccountUpdate;
    }

    @Override
    public void validate(@NonNull Object object, @NonNull Errors errors) {
        if (!(object instanceof UserRequest userRequest)) {
            throw new IllegalArgumentException("Expected UserRequest object");
        }

        if (!this.getIsAccountUpdate() && this.userRepository.existsByEmail(userRequest.getEmail())) {
            errors.rejectValue("email", "user.email.unique", "Email already used.");
        }

        // Ajoute d'autres validations personnalisées ici si besoin
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return UserRequest.class.equals(clazz);
    }
}
