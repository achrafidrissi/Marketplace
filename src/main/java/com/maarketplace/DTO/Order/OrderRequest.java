package com.maarketplace.DTO.Order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Cart ID is required")
    private Long cartId;
}
