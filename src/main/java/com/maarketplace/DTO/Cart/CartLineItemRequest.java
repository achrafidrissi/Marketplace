package com.maarketplace.DTO.Cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "ArticlePanier", description = "Représente un article individuel ajouté au panier par un utilisateur.")
public class CartLineItemRequest {

    @NotNull(message = "Product ID is required.")
    @Schema(description = "Identifiant du produit à ajouter", example = "12")
    private Long productId;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Minimum quantity is 1.")
    @Max(value = 100, message = "Maximum quantity is 100.")
    @Schema(description = "Quantité demandée pour ce produit", example = "3")
    private Integer quantity;
}
