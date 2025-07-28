package com.maarketplace.model;

import com.maarketplace.helpers.constants.GlobalValues;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "Credentials")
@Table(name = "Credentials", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "credentials_username_unique", columnNames = "username"))
@EntityListeners(AuditingEntityListener.class)
public class Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "inserted_at", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime insertedAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Credentials() {
    }

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public String toString() {
        return "Credentials: {" +
                // " id = " + this.getId() != null ? this.getId().toString() : "null" +
                ", username = " + this.getUsername() +
                " }";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Credentials credentials = (Credentials) object;
        return Objects.equals(this.getId(), credentials.getId()) || Objects.equals(this.getUsername(), credentials.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getUsername());
    }
}
