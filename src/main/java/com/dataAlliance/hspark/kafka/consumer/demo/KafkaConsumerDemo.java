package com.dataAlliance.hspark.kafka.consumer.demo;

import com.dataAlliance.hspark.kafka.consumer.HelloConsumer;

public class KafkaConsumerDemo {
	public static void main(String[] args) {
		HelloConsumer consumerThread = new HelloConsumer("c1");
		consumerThread.start();
	}
	
}
