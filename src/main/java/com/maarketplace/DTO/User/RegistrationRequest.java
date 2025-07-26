package com.maarketplace.DTO.User;

import com.maarketplace.DTO.Credentials.CredentialsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    @Valid
    private UserRequest user;

    @Valid
    private CredentialsRequest credentials;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}
