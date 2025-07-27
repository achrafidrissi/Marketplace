package com.maarketplace.controller;

import com.maarketplace.DTO.ProductCategory.ProductCategoryRequest;
import com.maarketplace.DTO.ProductCategory.ProductCategoryResponse;
import com.maarketplace.exception.NotFoundException;
import com.maarketplace.model.ProductCategory;
import com.maarketplace.repository.ProductCategoryRepository;
import com.maarketplace.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Product Categories", description = "Operations related to product categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;
    private final ProductCategoryRepository productCategoryRepository;

    @Operation(summary = "Get all product categories")
    @GetMapping
    public ResponseEntity<List<ProductCategoryResponse>> getAllCategories() {
        List<ProductCategory> categories = productCategoryService.getAllProductCategories();
        List<ProductCategoryResponse> response = categories.stream()
                .map(ProductCategoryResponse::new)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a category by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> getCategoryById(@PathVariable Long id) {
        ProductCategory category = productCategoryService.getProductCategory(id);
        if (category == null) {
            throw new NotFoundException("Category with ID " + id + " not found.");
        }
        return ResponseEntity.ok(new ProductCategoryResponse(category));
    }

    @Operation(summary = "Create a new category")
    @PostMapping
    public ResponseEntity<ProductCategoryResponse> createCategory(@Valid @RequestBody ProductCategoryRequest request) {
        ProductCategory category = request.toEntity();
        ProductCategory saved = productCategoryRepository.save(category); // Pas dans service car non présent
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductCategoryResponse(saved));
    }

    @Operation(summary = "Delete a category by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        ProductCategory category = productCategoryService.getProductCategory(id);
        if (category == null) {
            throw new NotFoundException("Category with ID " + id + " not found.");
        }
        productCategoryRepository.deleteById(id); // Pareil, direct repo si service ne le gère pas encore
        return ResponseEntity.noContent().build();
    }
}
