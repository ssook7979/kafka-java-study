package com.dataAlliance.hspark.kafka.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.serialization.StringDeserializer;

public class HelloConsumer extends Thread {
	private Properties properties;
	private KafkaConsumer<String, String> consumer;

	public HelloConsumer(String id) {
		properties = new Properties();
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "g1");
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, id);
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put("sasl.mechanism", "PLAIN");
		properties.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"client\" password=\"client-secret\";");
		
		
	}

	@Override
	public void run() {
		// TopicPartition partition = 
		consumer = new KafkaConsumer<String, String>(properties);
		consumer.subscribe(Collections.singletonList("test"));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
			
			for (ConsumerRecord<String, String> record: records) {
				System.out.println(record);
				consumer.commitSync();
			}
		}
		
	}
}
