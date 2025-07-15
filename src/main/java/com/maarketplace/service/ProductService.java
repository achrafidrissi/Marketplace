package com.maarketplace.service;

import com.maarketplace.helpers.Utils;
import com.maarketplace.model.Product;
import com.maarketplace.repository.ProductCategoryRepository;
import com.maarketplace.repository.ProductRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected ProductCategoryRepository productCategoryRepository;
    @Autowired
    protected CartLineItemService cartLineItemService;

    @Transactional
    public Product saveProduct(@NotNull Product product) {
        Float roundedPrice = Utils.roundNumberTo2Decimals(product.getPrice());
        product.setPrice(roundedPrice);
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(@NotNull Product productToUpdate, @NotNull Product product) {
        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(Utils.roundNumberTo2Decimals(product.getPrice()));
        productToUpdate.setCategory(product.getCategory());
        productToUpdate.setStockQuantity(product.getStockQuantity());

        return productRepository.save(productToUpdate);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }
}

