package com.maarketplace.controller.validator;

import com.maarketplace.model.User;
import com.maarketplace.repository.UserRepository;
import com.maarketplace.service.UserService;
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
        User user = (User) object;
        if (!this.getIsAccountUpdate() && this.userRepository.existsByEmail(user.getEmail())) {
            errors.rejectValue("email", "user.email.unique", "Email already used.");
        }
        // Removed nation check as it no longer exists
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return User.class.equals(clazz);
    }
}

