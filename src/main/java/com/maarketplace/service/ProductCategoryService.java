package com.maarketplace.service;

import com.maarketplace.model.ProductCategory;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public interface ProductCategoryService {

    ProductCategory getProductCategory(Long productCategoryId);

    List<ProductCategory> getAllProductCategories();

    ProductCategory saveCategory(@NotNull ProductCategory category);

    void deleteCategory(@NotNull Long id);
}
