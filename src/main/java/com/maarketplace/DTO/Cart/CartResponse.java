package com.maarketplace.DTO.Cart;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CartResponse {

    private Long cartId;
    private Long userId;
    private LocalDateTime insertedAt;
    private List<CartLineItemResponse> items;
    private Float totalPrice;
}
