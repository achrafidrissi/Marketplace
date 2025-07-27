package com.maarketplace.DTO.Product;

import com.maarketplace.model.Product;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank(message = "Product description is required.")
    @Size(min = 3, max = 60)
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    private Float price;

    @NotNull(message = "Stock quantity is required.")
    @Min(value = 0, message = "Stock cannot be negative.")
    private Integer stockQuantity;

    @NotNull(message = "Product category ID is required")
    private Long productCategoryId;


    public Product toEntity() {
        Product product = new Product();
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setStockQuantity(this.stockQuantity);
        return product;
    }

}
