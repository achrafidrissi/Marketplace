package com.maarketplace.service;

import com.maarketplace.exception.UserEmailNotExistsException;
import com.maarketplace.helpers.validators.TypeValidators;
import com.maarketplace.model.Cart;
import com.maarketplace.model.Credentials;
import com.maarketplace.model.User;
import com.maarketplace.repository.CartRepository;
import com.maarketplace.repository.CredentialsRepository;
import com.maarketplace.repository.OrderRepository;
import com.maarketplace.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected CredentialsRepository credentialsRepository;
    @Autowired
    protected CartRepository cartRepository;
    @Autowired
    protected OrderRepository orderRepository;

    public Boolean userExistsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUser(Long userId) {
        Optional<User> result = this.userRepository.findById(userId);
        return result.orElse(null);
    }

    public User getUser(Credentials credentials) {
        Optional<User> result = this.userRepository.findByCredentials(credentials);
        return result.orElse(null);
    }

    public User getUser(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotExistsException("User with email '" + email + "' does not exist."));
    }

    @Transactional
    public Cart getUserCurrentCart(Long userId) {
        User user = this.getUser(userId);
        if (user != null && !user.getCarts().isEmpty()) {
            return user.getCarts().get(user.getCarts().size() - 1);
        }
        return null;
    }

    @Transactional
    public User saveUser(@NotNull User user) {
        User savedUser = this.userRepository.save(user);
        Cart cart = new Cart(user);
        Cart savedCart = this.cartRepository.save(cart);
        savedUser.getCarts().add(savedCart);
        return savedUser;
    }

    @Transactional
    public User updateUser(Long userId, @NonNull User updatedUser) {
        Credentials updatedCredentials = updatedUser.getCredentials();
        User user = this.getUser(userId);
        if (user != null) {
            Credentials credentials = user.getCredentials();
            updatedCredentials.setInsertedAt(credentials.getInsertedAt());
            user.getCredentials().setUsername(updatedCredentials.getUsername());
            if (TypeValidators.validateString(updatedCredentials.getPassword())) {
                user.getCredentials().setPassword(updatedCredentials.getPassword());
            }
            user.getCredentials().preUpdate();
            user.setName(updatedUser.getName());
            user.setSurname(updatedUser.getSurname());
            user.setBirthDate(updatedUser.getBirthDate());
            return this.userRepository.save(user);
        }
        return null;
    }

    @Transactional
    public Boolean deleteUser(User user) {
        this.userRepository.delete(user);
        return !this.userRepository.existsById(user.getId());
    }
}
