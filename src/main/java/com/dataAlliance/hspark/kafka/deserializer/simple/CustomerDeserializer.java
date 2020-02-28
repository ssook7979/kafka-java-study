package com.dataAlliance.hspark.kafka.deserializer.simple;

import java.nio.ByteBuffer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.dataAlliance.hspark.kafka.obj.Customer;

public class CustomerDeserializer implements Deserializer<Customer> {

	@Override
	public void close() {
	}

	@Override
	public Customer deserialize(String topic, byte[] data) {
		int customerId;
		int nameSize;
		String customerName;
		
		try {
			if (data == null)  return null;
			if (data.length < 8)
				throw new SerializationException("Too short.");
			
			ByteBuffer buffer = ByteBuffer.wrap(data);
			
			customerId = buffer.getInt();
			nameSize = buffer.getInt();
			byte[] nameBytes = new byte[nameSize];
			buffer.get(nameBytes);
			customerName = new String(nameBytes, "UTF-8");
			
			return new Customer(customerId, customerName);
			
		} catch (Exception e) {
			throw new SerializationException("Serialization err.");
		}
	}

}
