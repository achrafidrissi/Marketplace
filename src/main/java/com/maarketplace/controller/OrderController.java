package com.maarketplace.controller;

import com.maarketplace.DTO.Order.OrderRequest;
import com.maarketplace.DTO.Order.OrderResponse;
import com.maarketplace.exception.NotFoundException;
import com.maarketplace.model.Cart;
import com.maarketplace.model.Order;
import com.maarketplace.model.User;
import com.maarketplace.service.CartService;
import com.maarketplace.service.OrderService;
import com.maarketplace.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders",
        description = "API pour créer, consulter, annuler ou suivre les commandes passées dans le marketplace."
)
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    @Operation(summary = "Create a new order")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        User user = userService.getUser(request.getUserId());
        if (user == null) throw new NotFoundException("User not found");

        Cart cart = cartService.getCart(request.getCartId());
        if (cart == null) throw new NotFoundException("Cart not found");

        Order order = new Order(user, cart);
        Order saved = orderService.saveOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponse(saved));
    }

    @Operation(summary = "Get an order by ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);
        if (order == null) throw new NotFoundException("Order not found");

        return ResponseEntity.ok(new OrderResponse(order));
    }

    @Operation(summary = "List all orders by user ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        List<Order> orders = orderService.getAllOrdersByUser(user);
        List<OrderResponse> response = orders.stream()
                .map(OrderResponse::new)
                .toList();

        return ResponseEntity.ok(response);
    }
}
