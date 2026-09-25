package com.microservice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	
	private Integer orderId;
	private Integer userId;
	private Integer productId;
	private Integer quantity;
    private Double totalPrice;
    private String status;
}
