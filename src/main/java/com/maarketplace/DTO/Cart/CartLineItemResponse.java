package com.maarketplace.DTO.Cart;

import com.maarketplace.model.CartLineItem;
import com.maarketplace.model.Product;
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
