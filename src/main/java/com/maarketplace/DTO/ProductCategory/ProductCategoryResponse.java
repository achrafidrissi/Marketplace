package com.maarketplace.DTO.ProductCategory;

import com.maarketplace.model.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryResponse {

    private Long id;
    private String name;
    private String description;

    public ProductCategoryResponse(ProductCategory category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
    }
}
