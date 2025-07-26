package com.maarketplace.DTO.Cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartLineItemResponse {

    private Long id;
    private Long productId;
    private String productName;
    private Float unitPrice;
    private Integer quantity;
    private Float totalPrice;
}
