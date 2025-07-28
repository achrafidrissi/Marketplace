package com.maarketplace.DTO.ProductCategory;

import com.maarketplace.model.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CatégorieProduitRéponse", description = "Détails d’une catégorie de produit.")
public class ProductCategoryResponse {

    @Schema(description = "Identifiant de la catégorie", example = "5")
    private Long id;

    @Schema(description = "Nom de la catégorie", example = "Objets personnalisés")
    private String name;

    @Schema(description = "Description", example = "Produits faits main")
    private String description;

    public ProductCategoryResponse(ProductCategory category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
    }
}
