package com.microservice.order.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.order.entities.Order;

public interface OrderRepositories extends JpaRepository<Order, Integer>{
	
	 List<Order> findByUserId(Integer userId);

	 List<Order> findByProductId(Integer productId);

}
