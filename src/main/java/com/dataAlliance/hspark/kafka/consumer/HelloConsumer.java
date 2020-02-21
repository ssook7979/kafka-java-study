package com.dataAlliance.hspark.kafka.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class HelloConsumer extends Thread {
	private Properties properties;
	private KafkaConsumer<String, String> consumer;

	public HelloConsumer(String id) {
		properties = new Properties();
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "g1");
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, id);
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091, localhost:9092, localhost:9093");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		
	}

	@Override
	public void run() {
		// TopicPartition partition = 
		consumer = new KafkaConsumer<String, String>(properties);
		consumer.subscribe(Collections.singletonList("partitioned-topic"));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
			
			for (ConsumerRecord<String, String> record: records) {
				System.out.println(record);
				consumer.commitSync();
			}
		}
		
	}
}
