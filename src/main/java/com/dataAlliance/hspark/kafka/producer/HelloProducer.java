package com.dataAlliance.hspark.kafka.producer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dataAlliance.hspark.kafka.security.oauth2.OAuthAuthenticateCallbackHandler;

public class HelloProducer extends Thread {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloProducer.class);
	private Properties properties;
	private KafkaProducer<String, String> producer;
	
	public HelloProducer() {
		properties = new Properties();
		
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, "p2");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put("sasl.mechanism", OAuthBearerLoginModule.OAUTHBEARER_MECHANISM);
		properties.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name);
		//properties.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"admin@test.com\" password=\"test\";");
		properties.put("sasl.jaas.config", 
				"org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required " + 
				"LoginStringClaim_sub=\"tester\" " + 
		        "username=\"admin@test.com\" " + 
				"password=\"test\" " +
				"OAUTH_SERVER=\"https://auth.data-alliance.com:3005\" " +
				"OAUTH_LOGIN_ENDPOINT=\"/oauth2/token\" " +
				"OAUTH_AUTHORIZATION=\"Basic MTY4YWM4NzMtNzdjMi00NDE3LWExMzItYjIwMmY4ODIzOWFhOjVjMjM1NzlkLWYwMGEtNDU3Yi04OTM4LTQzYTIxYWRhMjUzMQ==\";"
				);
		properties.put("sasl.login.callback.handler.class", OAuthAuthenticateCallbackHandler.class.getName());
		producer = new KafkaProducer<String, String>(properties);
	}
	
	@Override
	public void run() {
		String topic = "test";
		List<String> ids = Arrays.asList("93E0C3F9170F7E00","4A46E54ABF803200","4B6EB510CA789D00","AAAAAAAAAABBBBBB","BBBBAAAACCCC0000","BBBBAAAACCCC1111");

		while(true) {
			try {
				int index = getRandomIndex();
				// ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, getPartitionIndex(index), ids.get(index), getRandomMessage());
				ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "hello");
				// ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, ids.get(index), getRandomMessage());
				Future<RecordMetadata> future = producer.send(record);
				logger.info(future.get().toString());
				
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
}
