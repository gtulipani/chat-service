package com.chat.service.mapper;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.message.MessageCreationResponse;
import com.chat.entity.model.message.MessageResponse;
import com.chat.exception.CustomerMapperException;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class MessageMapper extends ConfigurableMapper {
	private static final String MAPPER_NOT_FOUND_ERROR = "Could not find any custom mapper to convert to %s";

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
		// All classes that must have a valid mapper against Message.class
		List<Class> classesToMap = Arrays.asList(
				MessageCreationRequest.class,
				MessageCreationResponse.class,
				MessageResponse.class);

		classesToMap.forEach(c -> mapperFactory.classMap(c, Message.class)
				.customize(customMappers.stream()
						.filter(mapper -> mapper.supports(c))
						.findFirst()
						.orElseThrow(() -> new CustomerMapperException(String.format(MAPPER_NOT_FOUND_ERROR, c))))
				.register());
	}
}
