package com.micro.userservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro.userservice.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);
	

}
