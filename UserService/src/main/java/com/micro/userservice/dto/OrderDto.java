package com.micro.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
	
	    private Integer orderId;
	    private Integer userId;
	    private Integer productId;
	    private Integer quantity;
	    private Double totalPrice;
	    private String status;
	
}
