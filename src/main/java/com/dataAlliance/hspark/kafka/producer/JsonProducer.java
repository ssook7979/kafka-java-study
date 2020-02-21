package com.dataAlliance.hspark.kafka.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.connect.json.JsonSerializer;

import com.dataAlliance.hspark.kafka.serializer.simple.KafkaJsonSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;

public class JsonProducer extends Thread {
	private Properties properties;
	private KafkaProducer<String, String> producer;
	int i = 0;
	
	public JsonProducer() {
		properties = new Properties();
				
		properties.put("bootstrap.servers", "localhost:9091, localhost:9092, localhost:9093");
		properties.put("client.id", "p1");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		
		producer = new KafkaProducer<String, String>(properties);
	}
	
	@Override
	public void run() {
		while(true) {
			i++;
			try {
				producer
				.send(new ProducerRecord<String, String>("test", "hello, request" + i))
				.get();
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
