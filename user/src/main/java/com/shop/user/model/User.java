package com.shop.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;

    @NotNull(message = "Valor name no puede ser nulo")
    @Size(min = 1, max = 50, message = "name debe tener entre 1 y 50 caracteres")
    private String name;

    @NotNull(message = "Valor email no puede ser nulo")
    @Size(min = 1, max = 50, message = "email debe tener entre 1 y 50 caracteres")
    private String email;

    @NotNull(message = "Valor phone no puede ser nulo")
    @Size(min = 1, max = 15, message = "phone debe tener entre 1 y 15 caracteres")
    private String phone;

    @NotNull(message = "Valor password no puede ser nulo")
    @Size(min = 1, max = 20, message = "password debe tener entre 1 y 20 caracteres")
    private String password;

    @NotNull(message = "Valor role no puede ser nulo")
    @Size(min = 1, max = 30, message = "role debe tener entre 1 y 30 caracteres")
    private String role;

    protected User() {
    }

    private User(Builder builder) {
        this.id_user = builder.id_user;
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.password = builder.password;
        this.role = builder.role; 
    }

    public Long getId_user() { return id_user; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public static class Builder {
        private Long id_user;
        private String name;
        private String email;
        private String phone;
        private String password;
        private String role;

        public User build() {     
            return new User(this);
        }

        public Builder setId(Long id_user) {
            this.id_user = id_user;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }
    }

}
