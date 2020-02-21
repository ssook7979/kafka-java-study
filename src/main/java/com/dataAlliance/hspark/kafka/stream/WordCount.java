package com.dataAlliance.hspark.kafka.stream;

import java.util.Properties;

import org.apache.kafka.streams.StreamsConfig;

public class WordCount {
	public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount");
        
        
	}
}
