package com.maarketplace.DTO.ProductCategory;

import com.maarketplace.model.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CatégorieProduitDemande", description = "Requête pour créer une nouvelle catégorie de produits.")
public class ProductCategoryRequest {

    @NotBlank
    @Schema(description = "Nom de la catégorie", example = "Objets personnalisés")
    private String name;

    @NotBlank
    @Schema(description = "Description de la catégorie", example = "Produits faits sur mesure pour collectionneurs")
    private String description;

    public ProductCategory toEntity() {
        return new ProductCategory(name, description);
    }
}
