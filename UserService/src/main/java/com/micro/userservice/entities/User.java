package com.micro.userservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "micro_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer userId;
	
	@Column(name = "NAME" , length=25 , nullable = false)
	private String name;
	
	@Column(name = "EMAIL" , unique = true , nullable = false)
	private String email;
	
	@Column(name = "ABOUT" , nullable = false)
	private String about;
	
}
