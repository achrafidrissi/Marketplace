package com.maarketplace.repository;

import com.maarketplace.model.Cart;
import com.maarketplace.model.CartLineItem;
import com.maarketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartLineItemRepository extends JpaRepository<CartLineItem, Long> {

    boolean existsByCartAndProduct(Cart cart, Product product);
}

