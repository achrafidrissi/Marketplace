package com.maarketplace.DTO.Cart;

import com.maarketplace.model.CartLineItem;
import com.maarketplace.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "ArticlePanierRéponse", description = "Détail d'un article du panier retourné à l'utilisateur.")
public class CartLineItemResponse {

    private Long id;
    private Long productId;
    private String productName;
    private Float unitPrice;
    private Integer quantity;
    private Float totalPrice;
    private Float productPrice;

    public CartLineItemResponse(CartLineItem item) {
        this.id = item.getId();
        Product product = item.getProduct();
        if (product != null) {
            this.productId = product.getId();
            this.productName = product.getName();
            this.productPrice = product.getPrice();
        }
        this.quantity = item.getQuantity();
    }
}
