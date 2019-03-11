package com.chat.service.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.message.MessageCreationResponse;
import com.chat.exception.CustomerMapperException;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class MessageMapper extends ConfigurableMapper {
	private MapperFactory mapperFactory;

	private final List<CustomMapperStrategy> customMappers;

	@Autowired
	public MessageMapper(List<CustomMapperStrategy> customMappers) {
		this.customMappers = customMappers;
		registerMappers();
	}

	@Override
	protected void configure(MapperFactory mapperFactory) {
		super.configure(mapperFactory);
		this.mapperFactory = mapperFactory;
	}

	/**
	 * We register mappers in a separated method, because {@link #configure(MapperFactory)} method is called before
	 * autowiring the {@link #customMappers}
	 */
	private void registerMappers() {
		mapperFactory.classMap(MessageCreationRequest.class, Message.class)
				.customize(customMappers.stream()
						.filter(mapper -> mapper.supports(MessageCreationRequest.class))
						.findFirst()
						.orElseThrow(() -> new CustomerMapperException("Could not find any custom mapper to convert to MessageCreationRequest")))
				.register();

		mapperFactory.classMap(MessageCreationResponse.class, Message.class)
				.customize(customMappers.stream()
						.filter(mapper -> mapper.supports(MessageCreationResponse.class))
						.findFirst()
						.orElseThrow(() -> new CustomerMapperException("Could not find any custom mapper to convert to MessageCreationResponse")))
				.register();
	}
}
