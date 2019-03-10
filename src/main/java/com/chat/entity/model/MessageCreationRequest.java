package com.chat.entity.model;

import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageCreationRequest {
	@NotBlank
	private Long sender;

	@NotBlank
	private Long recipient;

	@NotNull
	@NotEmpty
	Map<String, String> content;
}
