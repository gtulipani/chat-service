package com.chat.entity.model.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.testng.annotations.Test;

public class MessageContentTypeTest {
	@Test
	public void testOfDisplayName_text() {
		MessageContentType messageContentType = MessageContentType.TEXT;
		String displayValue = messageContentType.getDisplayValue();

		assertThat(MessageContentType.ofDisplayName(displayValue)).isEqualByComparingTo(messageContentType);
	}

	@Test
	public void testOfDisplayName_image() {
		MessageContentType messageContentType = MessageContentType.IMAGE;
		String displayValue = messageContentType.getDisplayValue();

		assertThat(MessageContentType.ofDisplayName(displayValue)).isEqualByComparingTo(messageContentType);
	}

	@Test
	public void testOfDisplayName_video() {
		MessageContentType messageContentType = MessageContentType.VIDEO;
		String displayValue = messageContentType.getDisplayValue();

		assertThat(MessageContentType.ofDisplayName(displayValue)).isEqualByComparingTo(messageContentType);
	}

	@Test
	public void testOfDisplayName_invalid_throwsIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> MessageContentType.ofDisplayName(null))
				.withMessage("Couldn't find MessageContentType with displayName=%s", "null");
	}
}
