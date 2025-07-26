package com.maarketplace.service;

import com.maarketplace.model.Cart;
import com.maarketplace.model.Credentials;
import com.maarketplace.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

public interface UserService {

    Boolean userExistsByEmail(String email);

    User getUser(Long userId);

    User getUser(Credentials credentials);

    User getUser(String email);

    Cart getUserCurrentCart(Long userId);

    User saveUser(@NotNull User user);

    User updateUser(Long userId, @NonNull User updatedUser);

    Boolean deleteUser(User user);
}
