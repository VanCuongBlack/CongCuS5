package com.example.TranVanCuong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    private double price;

    private String sku;

    public Product(String name, String description, double price, String sku) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sku = sku;
    }
}
