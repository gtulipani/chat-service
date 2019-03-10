package com.chat.service.mapper;

import static com.chat.mother.UserMother.aUser;
import static com.chat.mother.UserMother.aUserCreationResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.User;
import com.chat.entity.model.UserCreationResponseEntity;

public class UserMapperTest {
	private UserMapper userMapper;

	@BeforeMethod
	public void setup() {
		userMapper = new UserMapper();
	}

	@Test
	public void testMapUserToUserCreationResponseEntity() {
		User user = aUser();
		UserCreationResponseEntity userCreationResponseEntity = aUserCreationResponseEntity();

		assertThat(userMapper.map(user, UserCreationResponseEntity.class)).isEqualTo(userCreationResponseEntity);
	}
}
