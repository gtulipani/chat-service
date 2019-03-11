package com.chat.entity.model.message;

import java.sql.Timestamp;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageResponse {
	private Long id;
	private Timestamp timestamp;
	private Long sender;
	private Long recipient;
	Map<String, String> content;
}
