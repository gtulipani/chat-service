package com.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.chat.entity.model.User;
import com.chat.entity.model.message.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
	@Query("SELECT m FROM Message m WHERE m.recipient = ?1 AND m.id <= ?2 ORDER BY m.id DESC")
	Page<Message> findFirstMessageForRecipientAndIdLowerThan(User recipient, Long messageId, Pageable pageable);

	@Query("SELECT m FROM Message m WHERE m.recipient = ?1 AND m.id >= ?2 ORDER BY m.id ASC")
	Page<Message> findMessagesForRecipientAndIdLowerThanWithLimit(User recipient, Long messageId, Pageable pageable);
}
