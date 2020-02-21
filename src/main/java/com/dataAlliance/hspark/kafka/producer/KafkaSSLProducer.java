package com.dataAlliance.hspark.kafka.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.dataAlliance.hspark.kafka.utils.PropertiesUtils;

public class KafkaSSLProducer extends Thread{
	
	private KafkaProducer<String, String> producer;
	
	public KafkaSSLProducer(String path) {
		Properties props = PropertiesUtils.newBuilder()
				.setPropertiesFilePath(path)
				.build();
		
		producer = new KafkaProducer<String, String>(props);
	}
	
	@Override
	public void run() {
		String topic = "test";
		String value = "test-value";
		
		while(true) {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, value);
			try {
				producer.send(record)
						.get();
				Thread.sleep(10000);
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
