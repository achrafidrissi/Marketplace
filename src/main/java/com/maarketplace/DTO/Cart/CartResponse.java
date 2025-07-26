package com.maarketplace.DTO.Cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartResponse {

    private List<CartLineItemResponse> items;
    private Float totalPrice;
}
