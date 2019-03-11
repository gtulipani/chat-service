package com.chat.entity.model.message.content;

import java.io.Serializable;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MessageContent implements Serializable {
	private static final long serialVersionUID = -6507963547063710509L;

	protected MessageContentType messageContentType;
	protected Map<String, String> content;
}
