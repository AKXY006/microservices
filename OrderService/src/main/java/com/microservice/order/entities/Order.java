package com.microservice.order.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(name = "user_id", nullable = false)
    @NotNull
    private Integer userId;

    @Column(name = "product_id", nullable = false)
    @NotNull
    private Integer productId;

    @Column(name = "quantity", nullable = false)
    @NotNull
    @Positive
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    @NotNull
    @Positive
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull
    private Status status;
}