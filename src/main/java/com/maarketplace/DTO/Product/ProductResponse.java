package com.maarketplace.DTO.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Float price;
    private Integer stockQuantity;
    private Long categoryId;
    private String categoryName;
}
