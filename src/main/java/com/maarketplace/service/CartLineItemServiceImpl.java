package com.maarketplace.service;

import com.maarketplace.repository.CartLineItemRepository;
import org.springframework.stereotype.Service;

@Service
public class CartLineItemServiceImpl implements CartLineItemService {

    private final CartLineItemRepository cartLineItemRepository;

    public CartLineItemServiceImpl(CartLineItemRepository cartLineItemRepository) {
        this.cartLineItemRepository = cartLineItemRepository;
    }
}
