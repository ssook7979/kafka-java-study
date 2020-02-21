package com.dataAlliance.hspark.kafka.serializer.simple;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.kafka.common.serialization.Serializer;

import com.dataAlliance.hspark.kafka.obj.Customer;

public class CustomerSerializer implements Serializer<Customer>{

	@Override
	public byte[] serialize(String topic, Customer data) {
		byte[] serializedName = null;
		int stringSize;
		
		if (data == null) return null;
		else {
			if (data.getCustomerName() != null) {
				try {
					serializedName = data.getCustomerName().getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stringSize = serializedName.length;
				
			} else {
				serializedName = new byte[0];
				stringSize = 0;
			}
		}
		
		ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + stringSize);
		buffer.putInt(data.getCustomerID());
		buffer.putInt(stringSize);
		buffer.put(serializedName);
		return buffer.array();
	}

}
