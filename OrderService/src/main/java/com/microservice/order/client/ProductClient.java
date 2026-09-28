package com.microservice.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.order.dto.ProductDto;
import com.microservice.order.util.ResponseStructure;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {
	
	@GetMapping("/products/{productId}")
	ResponseStructure<ProductDto> getProductById(@PathVariable Integer productId);

}
