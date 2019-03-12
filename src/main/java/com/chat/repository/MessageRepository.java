package com.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.chat.entity.model.User;
import com.chat.entity.model.message.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
	Page<Message> findFirstByRecipientAndIdLessThanEqualOrderByIdDesc(User recipient, Long messageId, Pageable pageable);

	Page<Message> findAllByRecipientAndIdGreaterThanEqualOrderByIdAsc(User recipient, Long messageId, Pageable pageable);
}
