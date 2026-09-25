package com.microservice.order.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.order.entities.Order;

public interface OrderRepositories extends JpaRepository<Order, Integer>{
	
	 Optional<Order> findByUserId(Integer userId);

	 Optional<Order> findByProductId(Integer productId);

}
