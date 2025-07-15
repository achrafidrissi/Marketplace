package com.maarketplace.repository;

import com.maarketplace.model.Product;
import com.maarketplace.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Set<Product> findAllByNameContainingIgnoreCaseOrderById(String name);

    public Set<Product> findAllByCategoryOrderById(ProductCategory category);

    public Set<Product> findAllByNameContainingIgnoreCaseAndCategoryOrderById(String name, ProductCategory category);

    public Boolean existsByNameAndDescriptionAndPriceAndCategory(String name, String description, Float price, ProductCategory category);
}

