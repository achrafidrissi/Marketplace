package com.maarketplace.service;

import com.maarketplace.model.Cart;
import com.maarketplace.model.Order;
import com.maarketplace.model.User;
import com.maarketplace.repository.CartRepository;
import com.maarketplace.repository.OrderRepository;
import com.maarketplace.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;

    public Iterable<Order> getAllOrdersByUser(@NotNull User user) {
        return this.orderRepository.findAllByUserOrderByInsertedAtDesc(user);
    }

    @Transactional
    public Order makeOrder(Long userId) {
        Order savedOrder = null;
        User user = this.userRepository.findById(userId).orElse(null);
        if (user != null) {
            Cart cart = this.userService.getUserCurrentCart(user.getId());
            Order order = new Order(user, cart);
            savedOrder = this.orderRepository.save(order);
            Cart newCart = new Cart(user);
            Cart savedNewCart = this.cartRepository.save(newCart);
            user.getCarts().add(savedNewCart);
            this.userRepository.save(user);
        }
        return savedOrder;
    }

    public List<Object[]> getAllOrdersForUser(@NotNull Long userId) {
        return this.orderRepository.findAllByUserId(userId);
    }

}
