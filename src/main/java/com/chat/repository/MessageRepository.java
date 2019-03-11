package com.chat.repository;

import org.springframework.data.repository.CrudRepository;

import com.chat.entity.model.message.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
