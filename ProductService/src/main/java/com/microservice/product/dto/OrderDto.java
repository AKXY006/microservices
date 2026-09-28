package com.microservice.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	
    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Double totalPrice;
    private String status;

}
