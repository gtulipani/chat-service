package com.chat.service.mapper;

import ma.glasnost.orika.CustomMapper;

public abstract class CustomMapperStrategy<T, U> extends CustomMapper<T, U> {
	public abstract boolean supports(Class c);
}
