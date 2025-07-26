package com.maarketplace.service;

import com.maarketplace.exception.NotFoundException;
import com.maarketplace.helpers.Utils;
import com.maarketplace.helpers.constants.FieldSizes;
import com.maarketplace.model.Cart;
import com.maarketplace.model.CartLineItem;
import com.maarketplace.model.Product;
import com.maarketplace.repository.CartLineItemRepository;
import com.maarketplace.repository.CartRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartLineItemRepository cartLineItemRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           CartLineItemRepository cartLineItemRepository) {
        this.cartRepository = cartRepository;
        this.cartLineItemRepository = cartLineItemRepository;
    }

    @Override
    public Boolean existsCartLineItemProduct(@NotNull Cart cart, @NotNull Product product) {
        return cartLineItemRepository.existsByCartAndProduct(cart, product);
    }

    @Override
    @Transactional
    public CartLineItem makeCartLineItem(@NotNull Cart cart, @NotNull Product product, Integer quantity) {
        CartLineItem cartLineItem = new CartLineItem(cart, product, quantity);
        CartLineItem savedItem = cartLineItemRepository.save(cartLineItem);
        cart.addCartLineItem(cartLineItem);
        cartRepository.save(cart);
        return savedItem;
    }

    @Override
    @Transactional
    public Boolean deleteCartLineItem(@NotNull Cart cart, @NotNull Long cartLineItemId) {
        CartLineItem item = cart.getCartLineItem(cartLineItemId);
        if (item != null) {
            cart.removeCartLineItem(item);
            cartLineItemRepository.delete(item);
            cartRepository.save(cart);
            return true;
        }
        return false;
    }

    @Override
    public void updateCartLineItem(@NotNull Cart cart, @NotNull Long cartLineItemId, Integer quantity) {
        CartLineItem item = cart.getCartLineItem(cartLineItemId);
        if (item != null &&
                quantity >= FieldSizes.CARTLINEITEM_QUANTITY_MIN_VALUE &&
                quantity <= FieldSizes.CARTLINEITEM_QUANTITY_MAX_VALUE &&
                quantity <= item.getProduct().getStockQuantity()) {
            item.setQuantity(quantity);
            cartLineItemRepository.save(item);
            cartRepository.save(cart);
        }
    }

    @Override
    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart with ID " + cartId + " not found"));
    }

    @Override
    public Float calculateCartPrice(@NotNull Cart cart) {
        return Utils.roundNumberTo2Decimals(
                cart.getCartLineItems().stream()
                        .map(CartLineItem::getTotalPrice)
                        .reduce(0.0F, Float::sum)
        );
    }
}
