package com.dataAlliance.hspark.kafka2.multithreading;

import java.io.File;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dispatcher implements Runnable{
	final static Logger logger = LoggerFactory.getLogger(Dispatcher.class.getName());
	private final KafkaProducer<Integer, String> producer;
	private final String topicName;
	private final String fileLocation;
	
	Dispatcher(KafkaProducer<Integer, String> producer, String topicName, String fileLocation) {
        this.producer = producer;
        this.topicName = topicName;
        this.fileLocation = fileLocation;
    }
	
	@Override
	public void run() {
		logger.info("Start processing " + fileLocation + "....");
		File file = new File(fileLocation);
		int msgKey = 0;
		
		try {
			Scanner scanner = new Scanner(file);
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				producer.send(new ProducerRecord<Integer, String>(topicName, msgKey, line));
			}
		} catch (Exception e) {
			logger.error("Exception in thread " + fileLocation);
			throw new RuntimeException(e);
		}
	}
}
