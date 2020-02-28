package com.dataAlliance.hspark.kafka.deserializer.simple;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaJsonDeserializer<T> implements Deserializer<T> {
	@Override
	public void close() {
	}

	Class<T> type;
	
	public KafkaJsonDeserializer(Class<T> type) {
		type = this.type;
	}

	@Override
	public T deserialize(String topic, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
		T obj = null;
		try {
			obj = mapper.readValue(data, type);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return obj;
	}

}
