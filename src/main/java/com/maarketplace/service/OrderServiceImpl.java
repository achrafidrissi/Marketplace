package com.maarketplace.service;

import com.maarketplace.exception.NotFoundException;
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
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            UserService userService,
                            CartRepository cartRepository,
                            CartService cartService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    @Override
    public List<Order> getAllOrdersByUser(@NotNull User user) {
        return orderRepository.findAllByUserOrderByInsertedAtDesc(user);
    }

    @Override
    @Transactional
    public Order makeOrder(Long userId) {
        Order savedOrder = null;
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Cart cart = userService.getUserCurrentCart(user.getId());
            Order order = new Order(user, cart);
            savedOrder = orderRepository.save(order);

            Cart newCart = new Cart(user);
            Cart savedNewCart = cartRepository.save(newCart);
            user.getCarts().add(savedNewCart);
            userRepository.save(user);
        }
        return savedOrder;
    }

    @Override
    public List<Object[]> getAllOrdersForUser(@NotNull Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Order saveOrder(@NotNull Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(@NotNull Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order with ID " + orderId + " not found"));
    }

}
