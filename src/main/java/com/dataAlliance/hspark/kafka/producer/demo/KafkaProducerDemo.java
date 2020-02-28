package com.dataAlliance.hspark.kafka.producer.demo;

import com.dataAlliance.hspark.kafka.producer.HelloProducer;
import com.dataAlliance.hspark.kafka.producer.KafkaSSLProducer;

public class KafkaProducerDemo {
	public static void main(String[] args) {
		HelloProducer producerThread = new HelloProducer();
		// String path="resources\\security-test\\sasl\\client-config.properties";
		// KafkaSSLProducer producerThread = new KafkaSSLProducer(path);
		producerThread.start();
	}
}
