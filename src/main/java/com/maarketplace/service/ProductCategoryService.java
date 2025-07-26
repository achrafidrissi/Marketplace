package com.maarketplace.service;

import com.maarketplace.model.ProductCategory;

import java.util.List;
import java.util.Map;

public interface ProductCategoryService {

    ProductCategory getProductCategory(Long productCategoryId);

    List<ProductCategory> getAllProductCategories();

    Map<Long, ProductCategory> getAllProductCategoriesMap();
}
