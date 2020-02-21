package com.dataAlliance.hspark.kafka.obj;

import lombok.Getter;

@Getter
public class Customer {
	private int customerID;
	private String customerName;
	
	public Customer(int ID, String name) {
		this.customerID = ID;
		this.customerName = name;
	}
}
