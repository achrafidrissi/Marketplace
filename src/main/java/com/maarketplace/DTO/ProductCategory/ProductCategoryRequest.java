package com.maarketplace.DTO.ProductCategory;

import com.maarketplace.model.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    public ProductCategory toEntity() {
        return new ProductCategory(name, description);
    }
}
