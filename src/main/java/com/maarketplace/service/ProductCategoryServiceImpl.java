package com.maarketplace.service;

import com.maarketplace.model.ProductCategory;
import com.maarketplace.repository.ProductCategoryRepository;
import jakarta.validation.constraints.NotNull;
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
    public ProductCategory saveCategory(@NotNull ProductCategory category) {
        return productCategoryRepository.save(category);
    }

    @Override
    public void deleteCategory(@NotNull Long id) {
        productCategoryRepository.deleteById(id);
    }
}
