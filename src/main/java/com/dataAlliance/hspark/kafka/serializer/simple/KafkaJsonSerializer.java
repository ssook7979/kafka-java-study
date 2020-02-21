package com.dataAlliance.hspark.kafka.serializer.simple;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaJsonSerializer implements Serializer {

	@Override
	public byte[] serialize(String topic, Object data) {
		byte[] serVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			serVal = objectMapper.writeValueAsBytes(data);
		} catch (Exception e) {
			e.getStackTrace();
		}
		
		return serVal;
	}

}
