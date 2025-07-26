package com.maarketplace.service.prov;

import com.maarketplace.helpers.Utils;
import com.maarketplace.helpers.constants.FieldSizes;
import com.maarketplace.model.Cart;
import com.maarketplace.model.CartLineItem;
import com.maarketplace.model.Product;
import com.maarketplace.repository.CartLineItemRepository;
import com.maarketplace.repository.CartRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService1 {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartLineItemRepository cartLineItemRepository;

    public Boolean existsCartLineItemProduct(@NotNull Cart cart, @NotNull Product product) {
        return this.cartLineItemRepository.existsByCartAndProduct(cart, product);
    }

    @Transactional
    public CartLineItem makeCartLineItem(@NotNull Cart cart, @NotNull Product product, Integer quantity) {
        CartLineItem cartLineItem = new CartLineItem(cart, product, quantity    );
        CartLineItem cartLineItemSaved = this.cartLineItemRepository.save(cartLineItem);
        cart.addCartLineItem(cartLineItem);
        this.cartRepository.save(cart);
        return cartLineItemSaved;
    }

    @Transactional
    public Boolean deleteCartLineItem(@NotNull Cart cart, @NotNull Long cartLineItemId) {
        CartLineItem cartLineItemToRemove = cart.getCartLineItem(cartLineItemId);
        if (cartLineItemToRemove != null) {
            cart.removeCartLineItem(cartLineItemToRemove);
            this.cartLineItemRepository.delete(cartLineItemToRemove);
            this.cartRepository.save(cart);
            return true;
        } else {
            return false;
        }
    }

    public void updateCartLineItem(@NotNull Cart cart, @NotNull Long cartLineItemId, Integer quantity) {
        CartLineItem cartLineItemToUpdate = cart.getCartLineItem(cartLineItemId);
        if (cartLineItemToUpdate != null && quantity >= FieldSizes.CARTLINEITEM_QUANTITY_MIN_VALUE && quantity <= FieldSizes.CARTLINEITEM_QUANTITY_MAX_VALUE && quantity <= FieldSizes.CARTLINEITEM_QUANTITY_MAX_VALUE) {
            Product product = cartLineItemToUpdate.getProduct();
            if (quantity <= product.getStockQuantity()) {
                cartLineItemToUpdate.setQuantity(quantity);
                this.cartLineItemRepository.save(cartLineItemToUpdate);
                this.cartRepository.save(cart);
            }
        }
    }

    public Float calculateCartPrice(@NotNull Cart cart) {
        return Utils.roundNumberTo2Decimals(
                cart.getCartLineItems().stream()
                        .map(CartLineItem::getTotalPrice)  // assuming you have getTotalPrice()
                        .reduce(0.0F, Float::sum)
        );
    }
}

