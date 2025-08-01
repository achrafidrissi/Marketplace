package com.maarketplace.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Cart")
@Table(name = "Carts", uniqueConstraints = {@UniqueConstraint(name = "carts_user_insertedat_unique", columnNames = {"_user", "inserted_at"})})
@EntityListeners(AuditingEntityListener.class)
public class Cart {
    public final static Float CART_START_PRICE = 0.0F;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = User.class, optional = true)
    @JoinColumn(name = "_user", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "carts_users_fk"))
    private User user;

    @Column(name = "inserted_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime insertedAt;

    @OneToMany(targetEntity = CartLineItem.class, mappedBy = "cart", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy(value = "insertedAt DESC")
    private List<CartLineItem> cartLineItems;

    public Cart() {this.user = null;
        this.cartLineItems = new ArrayList<CartLineItem>();
    }

    public Cart(User user) {
        this.user = user;
        this.cartLineItems = new ArrayList<CartLineItem>();
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInsertedAt() {
        return this.insertedAt;
    }

    public void setInsertedAt(LocalDateTime insertedAt) {
        this.insertedAt = insertedAt;
    }

    public List<CartLineItem> getCartLineItems() {
        return this.cartLineItems;
    }

    public void setCartLineItems(List<CartLineItem> cartLineItems) {
        this.cartLineItems = cartLineItems;
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
        Cart cart = (Cart) object;
        return Objects.equals(this.getId(), cart.getId()) || (Objects.equals(this.getUser(), cart.getUser()) && Objects.equals(this.getInsertedAt(), cart.getInsertedAt()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getUser(), this.getInsertedAt());
    }

    public CartLineItem getCartLineItem(Long cartLineItemId) {
        return this.getCartLineItems().stream()
                .filter(cartLineItem -> cartLineItem.getId().equals(cartLineItemId))
                .findFirst().orElse(null);
    }

    public void addCartLineItem(CartLineItem cartLineItem) {
        this.getCartLineItems().add(cartLineItem);
    }

    public void removeCartLineItem(CartLineItem cartLineItem) {
        this.getCartLineItems().remove(cartLineItem);
    }
}
