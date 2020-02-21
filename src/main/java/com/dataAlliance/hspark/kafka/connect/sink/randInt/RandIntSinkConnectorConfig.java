package com.dataAlliance.hspark.kafka.connect.sink.randInt;

import java.util.Map;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

public class RandIntSinkConnectorConfig extends AbstractConfig {
	
	public static final String DATABASE_FILE_NAME_CONFIG = "database.filename";	
	public static final String DATABASE_FILE_NAME_DOC = "database filename";
	
	public RandIntSinkConnectorConfig(Map<String, String> originals) {
		super(config(), originals);
	}
	
	public static ConfigDef config() {
		return new ConfigDef()
				.define(DATABASE_FILE_NAME_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, DATABASE_FILE_NAME_DOC);
	}
}
