package com.chat.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.chat.entity.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsernameAndPassword(String username, String password);

	Boolean existsByToken(String token);
}
