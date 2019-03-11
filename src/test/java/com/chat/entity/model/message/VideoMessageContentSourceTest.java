package com.chat.entity.model.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.testng.annotations.Test;

import com.chat.entity.model.message.content.VideoMessageContentSource;

public class VideoMessageContentSourceTest {
	@Test
	public void testOfDisplayName_youtube() {
		VideoMessageContentSource videoMessageContentSource = VideoMessageContentSource.YOUTUBE;
		String displayValue = videoMessageContentSource.getDisplayValue();

		assertThat(VideoMessageContentSource.ofDisplayName(displayValue)).isEqualByComparingTo(videoMessageContentSource);
	}

	@Test
	public void testOfDisplayName_vimeo() {
		VideoMessageContentSource videoMessageContentSource = VideoMessageContentSource.VIMEO;
		String displayValue = videoMessageContentSource.getDisplayValue();

		assertThat(VideoMessageContentSource.ofDisplayName(displayValue)).isEqualByComparingTo(videoMessageContentSource);
	}

	@Test
	public void testOfDisplayName_invalid_throwsIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> VideoMessageContentSource.ofDisplayName(null))
				.withMessage("Couldn't find VideoMessageContentSource with displayName=%s", "null");
	}
}
