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
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public UserServiceImpl(UserRepository userRepository,
                           CredentialsRepository credentialsRepository,
                           CartRepository cartRepository,
                           OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getUser(Credentials credentials) {
        return userRepository.findByCredentials(credentials).orElse(null);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserEmailNotExistsException("User with email '" + email + "' does not exist."));
    }

    @Override
    @Transactional
    public Cart getUserCurrentCart(Long userId) {
        User user = getUser(userId);
        if (user != null && !user.getCarts().isEmpty()) {
            return user.getCarts().get(user.getCarts().size() - 1);
        }
        return null;
    }

    @Override
    @Transactional
    public User saveUser(@NotNull User user) {
        User savedUser = userRepository.save(user);
        Cart cart = new Cart(user);
        Cart savedCart = cartRepository.save(cart);
        savedUser.getCarts().add(savedCart);
        return savedUser;
    }

    @Override
    @Transactional
    public User updateUser(Long userId, @NonNull User updatedUser) {
        User user = getUser(userId);
        if (user != null) {
            Credentials updatedCredentials = updatedUser.getCredentials();
            Credentials credentials = user.getCredentials();

            updatedCredentials.setInsertedAt(credentials.getInsertedAt());
            credentials.setUsername(updatedCredentials.getUsername());

            if (TypeValidators.validateString(updatedCredentials.getPassword())) {
                credentials.setPassword(updatedCredentials.getPassword());
            }

            credentials.preUpdate();
            user.setName(updatedUser.getName());
            user.setSurname(updatedUser.getSurname());
            user.setBirthDate(updatedUser.getBirthDate());

            return userRepository.save(user);
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean deleteUser(User user) {
        userRepository.delete(user);
        return !userRepository.existsById(user.getId());
    }
}
