package com.maarketplace.DTO.User;

import com.maarketplace.model.User;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Surname is required.")
    private String surname;

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    private String email;

    @Past(message = "Birthdate must be in the past.")
    private LocalDate birthDate;

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
    private String password;

    public User toEntity() {
        User user = new User();
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setEmail(this.email);
        user.setBirthDate(this.birthDate);
        return user;
    }


    // Getters / Setters
}

