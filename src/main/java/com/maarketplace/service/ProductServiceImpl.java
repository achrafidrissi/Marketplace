package com.maarketplace.service;

import com.maarketplace.helpers.Utils;
import com.maarketplace.model.Product;
import com.maarketplace.repository.ProductCategoryRepository;
import com.maarketplace.repository.ProductRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CartLineItemService cartLineItemService;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductCategoryRepository productCategoryRepository,
                              CartLineItemService cartLineItemService) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.cartLineItemService = cartLineItemService;
    }

    @Override
    @Transactional
    public Product saveProduct(@NotNull Product product) {
        Float roundedPrice = Utils.roundNumberTo2Decimals(product.getPrice());
        product.setPrice(roundedPrice);
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(@NotNull Product productToUpdate, @NotNull Product product) {
        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(Utils.roundNumberTo2Decimals(product.getPrice()));
        productToUpdate.setCategory(product.getCategory());
        productToUpdate.setStockQuantity(product.getStockQuantity());

        return productRepository.save(productToUpdate);
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
