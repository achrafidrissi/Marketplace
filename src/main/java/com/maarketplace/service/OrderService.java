package com.maarketplace.service;

import com.maarketplace.model.Order;
import com.maarketplace.model.User;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface OrderService {

    Iterable<Order> getAllOrdersByUser(@NotNull User user);

    Order makeOrder(Long userId);

    List<Object[]> getAllOrdersForUser(@NotNull Long userId);
}
