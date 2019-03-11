package com.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.chat.entity.model.message.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
	@Query("SELECT m FROM Message m WHERE m.recipient = ?1 AND m.id <= ?2 ORDER BY m.id DESC LIMIT 1")
	Optional<Message> findFirstMessageForRecipientAndIdLowerThan(Long recipient, Long messageId);

	@Query("SELECT m FROM Message m WHERE m.recipient = ?1 AND m.id >= ?2 ORDER BY m.id ASC LIMIT ?3")
	List<Message> findMessagesForRecipientAndIdLowerThanWithLimit(Long recipient, Long messageId, Long limit);
}
