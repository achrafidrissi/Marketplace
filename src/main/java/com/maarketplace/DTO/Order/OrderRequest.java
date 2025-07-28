package com.maarketplace.DTO.Order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "CommandeDemande", description = "Requête pour passer une nouvelle commande.")
public class OrderRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Cart ID is required")
    @Schema(description = "Identifiant du panier", example = "78")
    private Long cartId;
}
