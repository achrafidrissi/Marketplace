package com.maarketplace.service;

import com.maarketplace.model.Cart;
import com.maarketplace.model.CartLineItem;
import com.maarketplace.model.Product;
import jakarta.validation.constraints.NotNull;

public interface CartService {

    Boolean existsCartLineItemProduct(@NotNull Cart cart, @NotNull Product product);

    CartLineItem makeCartLineItem(@NotNull Cart cart, @NotNull Product product, Integer quantity);

    Boolean deleteCartLineItem(@NotNull Cart cart, @NotNull Long cartLineItemId);

    void updateCartLineItem(@NotNull Cart cart, @NotNull Long cartLineItemId, Integer quantity);

    Cart getCart(Long cartId);

    Float calculateCartPrice(@NotNull Cart cart);
}
