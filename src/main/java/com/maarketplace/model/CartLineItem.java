package com.maarketplace.model;

import com.maarketplace.helpers.Utils;
import com.maarketplace.helpers.constants.GlobalValues;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "CartLineItem")
@Table(name = "cart_line_items", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "carts_user_unique", columnNames = {"cart", "product", "inserted_at"}))
public class CartLineItem {

    public static final Integer CARTLINEITEM_DEFAULT_QUANTITY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "cartlineitem_price", nullable = false)
    private Float unitPrice;

    @ManyToOne(targetEntity = Cart.class)
    @JoinColumn(name = "cart", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "cartlineitems_carts_fk"))
    private Cart cart;

    @ManyToOne(targetEntity = Product.class, optional = false)
    @JoinColumn(name = "product", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "products_products_fk"))
    private Product product;

    @Column(name = "inserted_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime insertedAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @Transient
    public Float getTotalPrice() {
        return Utils.roundNumberTo2Decimals(this.unitPrice * this.quantity);
    }


    public CartLineItem() {
        this.cart = null;
        this.product = null;
        this.quantity = CartLineItem.CARTLINEITEM_DEFAULT_QUANTITY;
        this.unitPrice = 0.0F;
    }

    public CartLineItem(Cart cart, Product product) {
        this.cart = cart;
        this.product = product;
        this.quantity = CARTLINEITEM_DEFAULT_QUANTITY;
        this.unitPrice = product.getPrice();
    }

    public CartLineItem(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCartLineItemPrice() {
        return this.unitPrice;
    }

    public void setCartLineItemPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setUser(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getInsertedAt() {
        return this.insertedAt;
    }

    public void setInsertedAt(LocalDateTime insertedAt) {
        this.insertedAt = insertedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void prePersist() {
        if (this.insertedAt == null) {
            this.insertedAt = LocalDateTime.now();
        }
        if (this.updatedAt == null) {
            this.updatedAt = this.insertedAt;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        CartLineItem cartLineItem = (CartLineItem) object;
        return Objects.equals(this.getId(), product.getId()) || (Objects.equals(this.getCart(), cartLineItem.getCart()) && Objects.equals(this.getProduct(), cartLineItem.getProduct()) && Objects.equals(this.getInsertedAt(), product.getInsertedAt()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getCart(), this.getProduct(), this.getInsertedAt());
    }

    @Override
    public String toString() {
        return "CartLineItem: {" +
                //"id = " + this.getId().toString() +
                ", quantity = " + this.getQuantity().toString() +
                ", unitPrice = " + this.getCartLineItemPrice().toString() +
                ", cart = " + this.getCart().toString() +
                ", product = " + this.getProduct().toString() +
                // ", insertedAt = " + this.getInsertedAt().toString() +
                // ", insertedAt = " + this.getInsertedAt().toString() +
                " }";
    }
}

