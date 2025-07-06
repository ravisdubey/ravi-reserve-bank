package com.demo.reserve.bank.account.api.serializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Currency;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class CustomBigDecimalSerializer implements RedisSerializer<Object> {

	private RedisSerializer<Object> jsonSerializer;

	public CustomBigDecimalSerializer(RedisSerializer<Object> jsonSerializer) {
		this.jsonSerializer = jsonSerializer;
	}

	@Override
	public byte[] serialize(Object value) throws SerializationException {
		if (value instanceof BigDecimal) {
			return value.toString().getBytes(StandardCharsets.UTF_8);
		}
		return jsonSerializer.serialize(value);
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null) {
			return null;
		}

		try {
			return new BigDecimal(new String(bytes, StandardCharsets.UTF_8));
		} catch (NumberFormatException e) {
			return jsonSerializer.deserialize(bytes);
		}
	}

}
