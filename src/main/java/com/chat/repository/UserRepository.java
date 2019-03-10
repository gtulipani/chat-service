package com.chat.repository;

import org.springframework.data.repository.CrudRepository;

import com.chat.entity.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
