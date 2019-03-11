package com.chat.entity.model.message;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageCreationResponse {
	private Long id;
	private Timestamp timestamp;
}
