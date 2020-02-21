package com.dataAlliance.hspark.kafka.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtils {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
	private Properties properties;

	public PropertiesUtils() {
		properties = new Properties();
	}
	
	public static PropertiesUtils newBuilder() {
		return new PropertiesUtils();
	}
	
	public PropertiesUtils setPropertiesFilePath(String path) {
		
		try {
			InputStream stream = new FileInputStream(path);
			properties.load(stream);
		} catch (IOException e) {
			logger.error("Invalid file path.", e);
		}
		
		return this;
	}
	
	public Properties build() {
		return properties;
	}
}
