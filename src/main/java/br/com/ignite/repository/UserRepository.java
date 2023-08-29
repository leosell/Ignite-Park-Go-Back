package br.com.ignite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ignite.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "SELECT u FROM User u WHERE u.email = ?1")
	Optional<User> findByEmail(String email);
	
	@Query(value = "SELECT u FROM User u WHERE u.email = ?1 AND u.password = ?2")
	Optional<User> findByEmailAndPassword(String email, String password);
}
