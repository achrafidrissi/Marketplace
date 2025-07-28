package com.maarketplace.controller;

import com.maarketplace.DTO.Product.ProductRequest;
import com.maarketplace.DTO.Product.ProductResponse;
import com.maarketplace.exception.NotFoundException;
import com.maarketplace.model.Product;
import com.maarketplace.model.ProductCategory;
import com.maarketplace.service.ProductCategoryService;
import com.maarketplace.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(
        name = "Produits",
        description = "API CRUD pour gérer les produits disponibles sur la plateforme : pièces anciennes, objets personnalisés, etc."
)

public class ProductController {

    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @Operation(summary = "Get all products")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a product by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            throw new NotFoundException("Product with ID " + id + " not found.");
        }
        return ResponseEntity.ok(new ProductResponse(product));
    }

    @Operation(summary = "Create a new product")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = request.toEntity();

        // (Optional) Check if category exists
        ProductCategory category = productCategoryService.getProductCategory(request.getProductCategoryId());
        if (category == null) {
            throw new NotFoundException("Product category not found.");
        }

        product.setCategory(category);
        Product saved = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(saved));
    }

    @Operation(summary = "Update a product by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @Valid @RequestBody ProductRequest request) {
        Product existing = productService.getProduct(id);
        if (existing == null) {
            throw new NotFoundException("Product with ID " + id + " not found.");
        }

        Product updated = request.toEntity();
        updated.setId(id);
        updated.setCategory(productCategoryService.getProductCategory(request.getProductCategoryId()));
        Product saved = productService.saveProduct(updated);
        return ResponseEntity.ok(new ProductResponse(saved));
    }

    @Operation(summary = "Delete a product by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            throw new NotFoundException("Product with ID " + id + " not found.");
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
