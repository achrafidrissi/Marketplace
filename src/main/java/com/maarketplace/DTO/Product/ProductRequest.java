package com.maarketplace.DTO.Product;

import com.maarketplace.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "ProduitDemande", description = "Données envoyées pour créer ou mettre à jour un produit.")
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 30)
    @Schema(description = "Nom du produit", example = "Ancienne pièce de 10 dirhams")
    private String name;

    @NotBlank(message = "Product description is required.")
    @Size(min = 3, max = 60)
    @Schema(description = "Description complète", example = "Pièce rare de collection frappée en 1987")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    @Schema(description = "Prix du produit", example = "250.00")
    private Float price;

    @NotNull(message = "Stock quantity is required.")
    @Min(value = 0, message = "Stock cannot be negative.")
    @Schema(description = "Quantité disponible", example = "7")
    private Integer stockQuantity;

    @NotNull(message = "Product category ID is required")
    @Schema(description = "Identifiant de la catégorie de produit", example = "3")
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
