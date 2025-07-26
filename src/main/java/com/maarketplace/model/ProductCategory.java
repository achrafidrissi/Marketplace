package com.maarketplace.model;

import com.maarketplace.helpers.constants.GlobalValues;
import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "ProductCategory")
@Table(name = "product_categories", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "productcategories_name_unique", columnNames = "name"))
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    public ProductCategory() {

    }

    public ProductCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        ProductCategory productCategory = (ProductCategory) object;
        return Objects.equals(this.getId(), productCategory.getId()) || Objects.equals(this.getName(), productCategory.getName());
    }

    @Override
    public String toString() {
        return "ProductCategory: {" +
                " id = " + this.getId().toString() +
                ", name = '" + this.getName() +
                ", description = '" + this.getDescription() +
                " }";
    }
}
