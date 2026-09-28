package com.microservice.order.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.order.entities.Order;
import com.microservice.order.entities.Status;

public interface OrderRepositories extends JpaRepository<Order, Integer>{
	
	 List<Order> findByUserId(Integer userId);

	 List<Order> findByProductId(Integer productId);
	 
	 boolean existsByUserIdAndStatusIn(Integer userId,List<Status> statuses);
	 
	 boolean existsByProductIdAndStatusIn(Integer productId, List<Status> statuses);

}
