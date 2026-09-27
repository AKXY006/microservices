package com.microservice.product.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(name = "product_name", nullable = false, unique = true, length = 100)
    @NotBlank
    @Size(max = 100)
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    @Positive
    private Double price;

    @Column(name = "quantity", nullable = false)
    @Positive
    private Integer quantity;
}