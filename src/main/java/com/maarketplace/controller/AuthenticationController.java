package com.maarketplace.controller;

import com.maarketplace.DTO.User.LoginRequest;
import com.maarketplace.DTO.User.LoginResponse;
import com.maarketplace.DTO.User.RegistrationRequest;
import com.maarketplace.DTO.User.UserResponse;
import com.maarketplace.controller.validator.CredentialsValidator;
import com.maarketplace.controller.validator.UserValidator;
import com.maarketplace.helpers.Utils;
import com.maarketplace.model.Credentials;
import com.maarketplace.model.User;
import com.maarketplace.service.CredentialsService;
import com.maarketplace.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final CredentialsService credentialsService;
    private final AuthenticationManager authenticationManager;
    private final UserValidator userValidator;
    private final CredentialsValidator credentialsValidator;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegistrationRequest request, BindingResult result) {
        credentialsValidator.setConfirmPassword(request.getConfirmPassword());

        userValidator.validate(request.getUser(), result);
        credentialsValidator.validate(request.getCredentials(), result);

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // Save User
        Credentials credentials = request.getCredentials().toEntity();
        Utils.cryptAndSaveUserCredentialsPassword(credentials, passwordEncoder);

        User user = request.getUser().toEntity();
        user.setCredentials(credentials);

        User savedUser = userService.saveUser(user);
        LOGGER.info("Registered new user with ID {}", savedUser.getId());

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        try {
            var token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Credentials dbCredentials = credentialsService.getCredentials(request.getUsername());
            User user = userService.getUser(dbCredentials);

            return ResponseEntity.ok(new LoginResponse("Login successful", dbCredentials.getUsername(), new UserResponse(user)));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }
}

