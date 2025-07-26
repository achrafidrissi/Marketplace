package com.maarketplace.controller;

import com.maarketplace.model.Cart;
import com.maarketplace.model.User;
import com.maarketplace.repository.CartRepository;
import com.maarketplace.repository.OrderRepository;
import com.maarketplace.repository.ProductRepository;
import com.maarketplace.service.prov.UserService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService1 userService;

    /**
     * Total number of products in catalog
     */
    @GetMapping("/products/count")
    public long getTotalProducts() {
        return productRepository.count();
    }

    /**
     * Total number of orders made by all users
     */
    @GetMapping("/orders/count")
    public long getTotalOrders() {
        return orderRepository.count();
    }

    /**
     * Get total number of orders for a given user
     */
    @GetMapping("/orders/count/{userId}")
    public long getUserOrdersCount(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        return user != null ? orderRepository.findAllByUserOrderByInsertedAtDesc(user).spliterator().getExactSizeIfKnown() : 0L;
    }

    /**
     * Number of items in current cart for a user
     */
    @GetMapping("/cart/items/count/{userId}")
    public int getUserCartItemCount(@PathVariable Long userId) {
        Cart cart = userService.getUserCurrentCart(userId);
        return cart != null ? cart.getCartLineItems().size() : 0;
    }
}

