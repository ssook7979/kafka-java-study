package com.dataAlliance.hspark.kafka.consumer;

import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonSerializer;

import com.dataAlliance.hspark.kafka.deserializer.simple.KafkaJsonDeserializer;

public class JsonConsumer extends Thread {
	
	
	@Override
	public void run() {
		Properties properties = new Properties();
		
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091, localhost:9092, localhost:9093");
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "c1");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonDeserializer.class.getName());

		//KafkaConsumer<String, CustomObject> consumer = new KafkaConsumer<String, CustomObject>(properties);
		JsonSerializer serializer = new JsonSerializer();
		//consumer.subscribe(Collections.singletonList("jsonTopic"));*/
	}
}
