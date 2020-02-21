package com.dataAlliance.hspark.kafka.connect.source.randInt;

import java.util.Map;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import lombok.Getter;

@Getter
public class RandomIntSourceConnectorConfig extends AbstractConfig {
	
	public static final String API_URL_CONFIG = "api.url";
	public static final String TOPIC_CONFIG = "topic";
	public static final String SLEEP_CONFIG = "sleep.seconds";
	
	public static final String API_ENDPOINT_DOC = "API URL";
	public static final String TOPIC_DOC = "Topic to write to";
	public static final String SLEEP_DOC = "Time in seconds that connector will wait until querying api again";
	
	public RandomIntSourceConnectorConfig(Map<String, String> originals) {
		super(config(), originals);
	}
	
	public static ConfigDef config() {
		return new ConfigDef()
				.define(API_URL_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, API_ENDPOINT_DOC)
				.define(TOPIC_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, TOPIC_DOC)
				.define(SLEEP_CONFIG, ConfigDef.Type.INT, 60, ConfigDef.Importance.MEDIUM, SLEEP_DOC);
	}
}
