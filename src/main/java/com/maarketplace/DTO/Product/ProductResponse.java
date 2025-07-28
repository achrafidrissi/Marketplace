package com.maarketplace.DTO.Product;

import com.maarketplace.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "ProduitRéponse", description = "Représente un produit disponible ou consulté.")
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Float price;
    private Integer stockQuantity;
    private Long categoryId;
    private String categoryName;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
        this.categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
    }
}
