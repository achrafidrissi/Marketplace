package com.maarketplace.DTO.User;

import com.maarketplace.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDate;
    private String username;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        // ajoute d'autres champs si nécessaire
    }

}
