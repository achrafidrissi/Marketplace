package com.maarketplace.service;

import com.maarketplace.model.ProductCategory;
import com.maarketplace.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {

    @Autowired
    protected ProductCategoryRepository productCategoryRepository;

    public ProductCategory getProductCategory(Long productCategoryId) {
        return this.productCategoryRepository.findById(productCategoryId).orElse(null);
    }

    public List<ProductCategory> getAllProductCategories() {
        return this.productCategoryRepository.findAllByOrderByName();
    }

    public Map<Long, ProductCategory> getAllProductCategoriesMap() {
        return getAllProductCategories().stream()
                .collect(Collectors.toMap(ProductCategory::getId, pc -> pc));
    }
}

