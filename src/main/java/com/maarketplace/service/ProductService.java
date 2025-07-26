package com.maarketplace.service;

import com.maarketplace.model.Product;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ProductService {

    Product saveProduct(@NotNull Product product);

    Product updateProduct(@NotNull Product productToUpdate, @NotNull Product product);

    Product getProduct(Long productId);

    List<Product> getAllProducts();

    void deleteProduct(Long productId);
}
