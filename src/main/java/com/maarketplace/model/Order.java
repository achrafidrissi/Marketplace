package com.maarketplace.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maarketplace.helpers.constants.FieldSizes;
import com.maarketplace.helpers.constants.GlobalValues;
import com.maarketplace.helpers.constants.Temporals;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jdk.jfr.Unsigned;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "Order")
@Table(name = "Orders", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "orders_user_cart_insertedat_unique", columnNames = {"_user", "cart", "inserted_at"}))
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "_user", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "orders_users_fk"))
    private User user;

    @OneToOne(targetEntity = Cart.class, optional = false)
    @JoinColumn(name = "cart", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "orders_carts_fk"))
    private Cart cart;

    @Column(name = "inserted_at", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime insertedAt;

    public Order() {
        this.user = null;
        this.cart = null;
    }

    public Order(User user, Cart cart) {
        this.user = user;
        this.cart = cart;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public LocalDateTime getInsertedAt() {
        return this.insertedAt;
    }

    public void setInsertedAt(LocalDateTime insertedAt) {
        this.insertedAt = insertedAt;
    }


    @PrePersist
    public void prePersist() {
        if (this.insertedAt == null) {
            this.insertedAt = LocalDateTime.now();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Order order = (Order) object;
        return Objects.equals(this.getId(), order.getId()) || (Objects.equals(this.getUser(), order.getUser()) && Objects.equals(this.getCart(), order.getCart()) && Objects.equals(this.getInsertedAt(), order.getInsertedAt()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getUser(), this.getCart(), this.getInsertedAt());
    }

    @Override
    public String toString() {
        return "Order: {" +
                //" id = " + this.getId() != null ? this.getId().toString() : "null" +
                ", user = " + this.getUser().toString() +
                ", cart = " + this.getCart().toString() +
                // ", insertedAt = " + this.getInsertedAt() != null ? this.getInsertedAt().toString() : "null" +
                " }";
    }
}

