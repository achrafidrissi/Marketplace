package com.maarketplace.service.prov;

import com.maarketplace.repository.CartLineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartLineItemService1 {
    @Autowired
    protected CartLineItemRepository cartLineItemRepository;
}
