package com.maarketplace.service;

import com.maarketplace.model.ProductCategory;
import com.maarketplace.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public ProductCategory getProductCategory(Long productCategoryId) {
        return productCategoryRepository.findById(productCategoryId).orElse(null);
    }

    @Override
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAllByOrderByName();
    }

    @Override
    public Map<Long, ProductCategory> getAllProductCategoriesMap() {
        return getAllProductCategories().stream()
                .collect(Collectors.toMap(ProductCategory::getId, pc -> pc));
    }
}
