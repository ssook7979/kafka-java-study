package com.dataAlliance.hspark.kafka.producer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
//import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;

//import com.dataAlliance.hspark.kafka.obj.CustomObject;


public class AsynchProducer extends Thread {
	private Properties properties;
	private KafkaProducer<String, String> producer;
	
	public AsynchProducer() {
		properties = new Properties();
				
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091, localhost:9092, localhost:9093");
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, "p1");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		// 0: no ack 1: when leader receives message all: when all in-sync replicas received the msg
		properties.put(ProducerConfig.ACKS_CONFIG, 0);
		
		//properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomObject.class.getName());
		producer = new KafkaProducer<String, String>(properties);
	}
	
	@Override
	public void run() {
		String topic = "partitioned-topic";
		List<String> ids = Arrays.asList("93E0C3F9170F7E00","4A46E54ABF803200","4B6EB510CA789D00","AAAAAAAAAABBBBBB","BBBBAAAACCCC0000","BBBBAAAACCCC1111");
		
		
		while(true) {
			try {
				int index = getRandomIndex();
				ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, getPartitionIndex(index), ids.get(index), getRandomMessage());
				// ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, ids.get(index), getRandomMessage());
				producer.send(record)
						.get();
				System.out.println(record);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	private String getRandomMessage() {
		Random rand = new Random();
		if (rand.nextBoolean()) {
			return "true";
		}
		return "false";
	}
	
	private int getRandomIndex() {
		Random rand = new Random();
		return rand.nextInt(6);
	}
	
	private int getPartitionIndex(int index) {
		return index % 3;
	}
	
	private class AsynchProducerCallback implements Callback {

		@Override
		public void onCompletion(RecordMetadata metadata, Exception exception) {
			if (exception != null) {
				exception.printStackTrace();
			}
			
		}
		
	}
}
