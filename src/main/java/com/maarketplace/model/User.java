package com.maarketplace.model;

import com.maarketplace.helpers.constants.FieldSizes;
import com.maarketplace.helpers.constants.GlobalValues;
import com.maarketplace.helpers.constants.Temporals;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jdk.jfr.Unsigned;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "User")
@Table(name = "Users", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = {@UniqueConstraint(name = "users_email_unique", columnNames = "email"), @UniqueConstraint(name = "users_credentials_unique", columnNames = "credentials")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Unsigned
    @Min(value = FieldSizes.ENTITY_ID_MIN_VALUE)
    private Long id;

    @NotBlank(message = "Name is a mandatory field.")
    @Size(min = FieldSizes.NAME_MIN_LENGTH, max = FieldSizes.NAME_MAX_LENGTH)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Surname is a mandatory field.")
    @Size(min = FieldSizes.SURNAME_MIN_LENGTH, max = FieldSizes.SURNAME_MAX_LENGTH)
    @Column(name = "surname", nullable = false)
    private String surname;

    @DateTimeFormat(pattern = Temporals.DATE_FORMAT)
    @Past(message = "Birth date must be in the past.")
    @Column(name = "birthdate", nullable = true)
    @Temporal(value = TemporalType.DATE)
    private Date birthDate;

    @NotBlank(message = "Email is a mandatory field.")
    @Email(message = "Invalid email format.")
    @Size(min = FieldSizes.EMAIL_MIN_LENGTH, max = FieldSizes.EMAIL_MAX_LENGTH)
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Credentials.class, optional = false, orphanRemoval = true)
    @JoinColumn(name = "credentials", referencedColumnName = "id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "users_credentials_fk"))
    private Credentials credentials;

    @OneToMany(targetEntity = Cart.class, orphanRemoval = false, mappedBy = "user", cascade = {CascadeType.REMOVE})
    //@OrderBy(value = "insertedAt ASC")
    private List<Cart> carts;

    public User() {
        this.carts = new ArrayList<Cart>();
        this.credentials = null;
    }

    public User(String name, String surname, String email, Float balance, Credentials credentials) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.credentials = credentials;
    }

    public User(String name, String surname, String email, Float balance, Credentials credentials, Date birthDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.email = email;
        this.credentials = credentials;
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

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public List<Cart> getCarts() {
        return this.carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        User user = (User) object;
        return Objects.equals(this.getId(), user.getId()) || Objects.equals(this.getEmail(), user.getEmail()) || Objects.equals(this.getCredentials(), user.getCredentials());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getEmail(), this.getCredentials());
    }

    @Override
    public String toString() {
        return "User: {" +
                //"id = " + this.getId() != null ? this.getId().toString() : "null" +
                ", name = '" + this.getName() +
                ", surname = '" + this.getSurname() +
                ", birthDate = " + (this.getBirthDate() != null ? this.getBirthDate().toString() : "Not-present") +
                ", email = '" + this.getEmail() +
                ", credentials = " + this.getCredentials().toString() +
                // ", carts = " + this.getCarts().toString() +
                " }";
    }
}
