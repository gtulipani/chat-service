package com.chat.service.mapper;

import org.springframework.stereotype.Component;

import com.chat.entity.model.User;
import com.chat.entity.model.UserCreationResponseEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class UserMapper extends ConfigurableMapper {
	@Override
	protected void configure(MapperFactory factory) {
		super.configure(factory);

		factory.classMap(User.class, UserCreationResponseEntity.class)
				.byDefault()
				.register();
	}
}
