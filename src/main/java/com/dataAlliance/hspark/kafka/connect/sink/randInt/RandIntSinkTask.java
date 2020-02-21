package com.dataAlliance.hspark.kafka.connect.sink.randInt;

import static com.dataAlliance.hspark.kafka.connect.sink.randInt.RandIntSinkConnectorConfig.DATABASE_FILE_NAME_CONFIG;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dataAlliance.hspark.kafka.connect.source.randInt.RandomIntSourceConnectorConfig;
import com.dataAlliance.hspark.kafka.connect.source.util.Version;
import com.dataAlliance.hspark.kafka.connect.sqlite.DataWriter;

public class RandIntSinkTask extends SinkTask {
	private static Logger logger = LoggerFactory.getLogger(RandIntSinkTask.class);
	private RandomIntSourceConnectorConfig randomIntSinkConnectorConfig;
	
	private String database;
	
	private CountDownLatch stopLatch = new CountDownLatch(1);
	private DataWriter dataWriter;
	
	@Override
	public String version() {
		return Version.getVersion();
	}

	@Override
	public void start(Map<String, String> props) {
		logger.info("Starting sink task with properties {}", props);
		
		randomIntSinkConnectorConfig = new RandomIntSourceConnectorConfig(props);
		database = randomIntSinkConnectorConfig.getString(DATABASE_FILE_NAME_CONFIG);
		dataWriter = new DataWriter(database);
	}

	@Override
	public void put(Collection<SinkRecord> records) {
		for (SinkRecord record: records) {
			dataWriter.put(Integer.parseInt((String) record.value()), record.topic(), record.timestamp());
			
		}
		
	}

	@Override
	public synchronized void stop() {
		logger.info("Stopping sink task");
		stopLatch.countDown();
	}

}
