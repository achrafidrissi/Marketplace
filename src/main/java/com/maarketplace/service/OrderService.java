package com.maarketplace.service;

import com.maarketplace.model.Order;
import com.maarketplace.model.User;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrdersByUser(@NotNull User user);

    Order makeOrder(Long userId);

    Order saveOrder(@NotNull Order order);

    Order getOrder(@NotNull Long orderId);

    List<Object[]> getAllOrdersForUser(@NotNull Long userId);
}
