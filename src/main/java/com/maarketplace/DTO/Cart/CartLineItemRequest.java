package com.maarketplace.DTO.Cart;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartLineItemRequest {

    @NotNull(message = "Product ID is required.")
    private Long productId;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Minimum quantity is 1.")
    @Max(value = 100, message = "Maximum quantity is 100.")
    private Integer quantity;
}
