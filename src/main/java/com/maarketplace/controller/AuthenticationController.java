package com.maarketplace.controller;

import com.maarketplace.DTO.RegistrationRequest;
import com.maarketplace.controller.validator.CredentialsValidator;
import com.maarketplace.controller.validator.UserValidator;
import com.maarketplace.helpers.Utils;
import com.maarketplace.model.Credentials;
import com.maarketplace.model.User;
import com.maarketplace.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private CredentialsValidator credentialsValidator;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody @Valid RegistrationRequest request,
            BindingResult bindingResult
    ) {
        User user = request.getUser();
        Credentials credentials = request.getCredentials();
        String confirmPassword = request.getConfirmPassword();

        credentialsValidator.setConfirmPassword(confirmPassword);
        userValidator.validate(user, bindingResult);
        credentialsValidator.validate(credentials, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Utils.cryptAndSaveUserCredentialsPassword(credentials, passwordEncoder);
        user.setCredentials(credentials);
        User savedUser = userService.saveUser(user);

        if (savedUser != null) {
            LOGGER.info("Registered account with User ID: {}", savedUser.getId());
            return ResponseEntity.ok("User registered successfully.");
        } else {
            LOGGER.error("User registration failed.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed.");
        }
    }
}

