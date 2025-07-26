package com.maarketplace.DTO.Order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maarketplace.model.Cart;
import com.maarketplace.model.Order;
import com.maarketplace.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponse {

    private Long id;
    private Long userId;
    private String userFullName;
    private Long cartId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertedAt;

    public OrderResponse(Order order) {
        this.id = order.getId();

        User user = order.getUser();
        this.userId = user.getId();
        this.userFullName = user.getName() + " " + user.getSurname();

        Cart cart = order.getCart();
        this.cartId = cart.getId();

        this.insertedAt = order.getInsertedAt();
    }
}
