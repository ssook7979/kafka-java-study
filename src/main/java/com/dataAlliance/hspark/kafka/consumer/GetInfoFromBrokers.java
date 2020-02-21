package com.dataAlliance.hspark.kafka.consumer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

public class GetInfoFromBrokers {
	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "g1");
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "c1");
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091, localhost:9092, localhost:9093");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		
		TopicPartition partition = new TopicPartition("partitioned-topic", 0);
		Set<TopicPartition> partitionSet = new HashSet<TopicPartition>();
		partitionSet.add(partition);
		
		System.out.println(consumer.committed(partitionSet));
		Map<TopicPartition, Long> timestampsToSearch = new HashMap<TopicPartition, Long>();
		timestampsToSearch.put(partition, System.currentTimeMillis() - 5000);
		System.out.println(consumer.offsetsForTimes(timestampsToSearch));
		consumer.close();
	}
}
