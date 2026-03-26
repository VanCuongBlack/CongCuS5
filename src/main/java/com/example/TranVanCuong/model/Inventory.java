package com.example.TranVanCuong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import jakarta.validation.constraints.Min;

@Document(collection = "inventories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    private String id;

    @DBRef(lazy = false)
    private Product product;

    @Min(value = 0, message = "Stock cannot be negative")
    private long stock = 0;

    @Min(value = 0, message = "Reserved cannot be negative")
    private long reserved = 0;

    @Min(value = 0, message = "SoldCount cannot be negative")
    private long soldCount = 0;

    public Inventory(Product product) {
        this.product = product;
        this.stock = 0;
        this.reserved = 0;
        this.soldCount = 0;
    }
}
