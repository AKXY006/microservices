package com.micro.userservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.micro.userservice.dto.OrderDto;
import com.micro.userservice.util.ResponseStructure;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {
	
	@GetMapping("/orders/user/{userId}")
	ResponseStructure<List<OrderDto>> getOrderByUserId(@PathVariable Integer userId);
	
	@GetMapping("/orders/user/{userId}/active")
	ResponseEntity<Boolean> hasActiveOrder(@PathVariable Integer userId);
	

}