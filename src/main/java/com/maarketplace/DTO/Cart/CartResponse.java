package com.maarketplace.DTO.Cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "PanierUtilisateur", description = "Contient tous les articles présents dans le panier d’un utilisateur.")
public class CartResponse {

    @Schema(description = "Liste des articles du panier")
    private List<CartLineItemResponse> items;

    @Schema(description = "Total du panier en dirhams", example = "300.00")
    private Float totalPrice;
}
