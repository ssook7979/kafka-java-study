package com.dataAlliance.hspark.kafka.consumer;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class CustomerConsumer extends Thread {

	@Override
	public void run() {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleNewConsumer");
		properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("auto.offset.reset", "latest");
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		
	}
	
}
