package com.dataAlliance.hspark.kafka.connect.source.randInt;

import static com.dataAlliance.hspark.kafka.connect.source.randInt.RandomIntSourceConnectorConfig.API_URL_CONFIG;
import static com.dataAlliance.hspark.kafka.connect.source.randInt.RandomIntSourceConnectorConfig.SLEEP_CONFIG;
import static com.dataAlliance.hspark.kafka.connect.source.randInt.RandomIntSourceConnectorConfig.TOPIC_CONFIG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import com.dataAlliance.hspark.kafka.connect.source.util.Version;

public class RandomIntSourceConnector extends SourceConnector {
	private RandomIntSourceConnectorConfig randomIntSourceConnectorConfig;

	@Override
	public String version() {
		return Version.getVersion();
	}

	@Override
	public void start(Map<String, String> props) {
		randomIntSourceConnectorConfig = new RandomIntSourceConnectorConfig(props);
	}

	@Override
	public Class<? extends Task> taskClass() {
		return RandomIntSourceTask.class;
	}

	@Override
	public List<Map<String, String>> taskConfigs(int maxTasks) {
		List<Map<String, String>> configs = new ArrayList<Map<String, String>>(maxTasks);
        for (int i = 0; i < maxTasks; i++) {
            Map<String, String> config = new HashMap<String, String>(3);
            config.put(API_URL_CONFIG, randomIntSourceConnectorConfig.getString(API_URL_CONFIG));
            config.put(SLEEP_CONFIG, Integer.toString(randomIntSourceConnectorConfig.getInt(SLEEP_CONFIG)));
            config.put(TOPIC_CONFIG, randomIntSourceConnectorConfig.getString(TOPIC_CONFIG));
            configs.add(config);
        }
        return configs;
	}

	@Override
	public void stop() {
	}

	@Override
	public ConfigDef config() {
		return RandomIntSourceConnectorConfig.config();
	}

}
