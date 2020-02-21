package com.dataAlliance.hspark.kafka.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class SampleProducer extends Thread {
	
	
	@Override
	public void run() {
		Properties props = new Properties();
		
		props.put("bootstrap.servers", "localhost:9092");
		props.put("client.id", "my_producer");
		props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		KafkaProducer<Integer, String> producer = new KafkaProducer<Integer, String>(props);	
		
		int messageNo = 1;
		
		while(true) {
			String messageStr = "Message_" + messageNo;
			
			try {
				producer.send(new ProducerRecord<Integer, String>("sampleTopic", messageNo, messageStr)).get();
				System.out.println("Sent message: (" + messageNo + ", " + messageStr + ")");
				messageNo++;
				Thread.sleep(5000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				
			} catch (ExecutionException e) {
				e.printStackTrace();
				
			}
			
		}
		
	}
}
