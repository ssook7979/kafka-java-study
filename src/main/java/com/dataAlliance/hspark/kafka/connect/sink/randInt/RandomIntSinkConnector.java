package com.dataAlliance.hspark.kafka.connect.sink.randInt;

import static com.dataAlliance.hspark.kafka.connect.sink.randInt.RandIntSinkConnectorConfig.DATABASE_FILE_NAME_CONFIG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

import com.dataAlliance.hspark.kafka.connect.sink.util.Version;

public class RandomIntSinkConnector extends SinkConnector {
	private RandIntSinkConnectorConfig randIntSinkConnectorConfig;

	@Override
	public String version() {
		return Version.getVersion();
	}

	@Override
	public void start(Map<String, String> props) {
		randIntSinkConnectorConfig = new RandIntSinkConnectorConfig(props);
		
	}

	@Override
	public Class<? extends Task> taskClass() {
		return RandIntSinkTask.class;
	}

	@Override
	public List<Map<String, String>> taskConfigs(int maxTasks) {
		List<Map<String, String>> configs = new ArrayList<Map<String, String>>(maxTasks);
        for (int i = 0; i < maxTasks; i++) {
            Map<String, String> config = new HashMap<String, String>(3);
            config.put(DATABASE_FILE_NAME_CONFIG, randIntSinkConnectorConfig.getString(DATABASE_FILE_NAME_CONFIG));
            configs.add(config);
        }
        return configs;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ConfigDef config() {
		return RandIntSinkConnectorConfig.config();
	}

}
