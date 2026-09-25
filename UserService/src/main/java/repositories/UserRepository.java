package repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.User;

public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByEmail(String email);
	

}
