package com.shop.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_product;

    @NotNull(message = "Valor SKU no puede ser nulo")
    @Size(min = 1, max = 100, message = "SKU debe tener entre 1 y 100 caracteres")
    private String sku;

    @NotNull(message = "Valor name no puede ser nulo")
    @Size(min = 1, max = 100, message = "name debe tener entre 1 y 100 caracteres")
    private String name;

    @Min(value = 0, message = "Valor price no puede ser negativo")
    @Max(value = 999999999, message = "Valor price no puede ser superior a 999999999")
    private Long price;

    @Min(value = 0, message = "Valor discount no puede ser negativo")
    @Max(value = 100, message = "Valor discount debe estar entre 0 y 100")
    private int discount;

    @NotNull(message = "Valor category no puede ser nulo")
    @Size(min = 1, max = 100, message = "category debe tener entre 1 y 100 caracteres")
    private String category;

    @NotNull(message = "Valor description no puede ser nulo")
    @Size(min = 1, max = 1000, message = "description debe tener entre 1 y 1000 caracteres")
    private String description;

    @Min(value = 0, message = "Valor de stock no puede ser negativo")
    @Max(value = 999999, message = "Valor stock no puede ser superior a 999999")
    private int stock;

    protected Products() {
    }

    private Products(Builder builder) {
        this.id_product = builder.id_product;
        this.sku = builder.sku;
        this.name = builder.name;
        this.price = builder.price;
        this.discount = builder.discount;
        this.category = builder.category;
        this.description = builder.description;
        this.stock = builder.stock;
    }

    public Long getId_product() { return id_product; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public Long getPrice() { return price; }
    public int getDiscount() { return discount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public int getStock() { return stock; }

    public static class Builder {
        private Long id_product;
        private String sku;
        private String name;
        private Long price;
        private Integer discount;
        private String category;
        private String description;
        private int stock;

        public Products build() {
            return new Products(this);
        }

        public Builder setId_product(Long id_product) {
            this.id_product = id_product;
            return this;
        }

        public Builder setSku(String sku) {
            this.sku = sku;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPrice(Long price) {
            this.price = price;
            return this;
        }

        public Builder setDiscount(Integer discount) {
            this.discount = discount;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setStock(int stock) {
            this.stock = stock;
            return this;
        }
        
    }

}