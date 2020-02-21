package com.dataAlliance.hspark.kafka.connect.source.randInt;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dataAlliance.hspark.kafka.connect.source.util.Version;

import static com.dataAlliance.hspark.kafka.connect.source.util.RandomIntHttpClient.httpClient;
import static com.dataAlliance.hspark.kafka.connect.source.util.RandomIntSchemas.VALUE_SCHEMA;


public class RandomIntSourceTask extends SourceTask {
	private static Logger logger = LoggerFactory.getLogger(RandomIntSourceTask.class);
	private RandomIntSourceConnectorConfig rlscc;
	
	private int sleepInSeconds;
	private String apiUrl;
	private String topic;
	
	private CountDownLatch stopLatch = new CountDownLatch(1);
	private boolean shouldWait = false;
	
	@Override
	public String version() {
		return Version.getVersion();
	}

	@Override
	public void start(Map<String, String> props) {
		logger.info("Starting source task with properties {}", props);
		
		rlscc = new RandomIntSourceConnectorConfig(props);
		sleepInSeconds = rlscc.getInt(RandomIntSourceConnectorConfig.SLEEP_CONFIG);
		apiUrl = rlscc.getString(RandomIntSourceConnectorConfig.API_URL_CONFIG);
		topic = rlscc.getString(RandomIntSourceConnectorConfig.TOPIC_CONFIG);
	}

	@Override
	public List<SourceRecord> poll() throws InterruptedException {
		boolean shouldStop = false;
		
		if (shouldWait) {
			logger.debug("Waiting for {} seconds for the next poll", sleepInSeconds);
	        shouldStop = stopLatch.await(sleepInSeconds, TimeUnit.SECONDS);
	        
		}
		
		if (!shouldStop) {
			logger.debug("Started new polling.");
			shouldWait = true;
			return getSourceRecords();
		} else {
			logger.debug("Received signal to stop, didn't poll anything");
			return null;
		}
	}

	@Override
	public synchronized void stop() {
		logger.info("Stopping source task");
		stopLatch.countDown();
	}

	private List<SourceRecord> getSourceRecords() {
		SourceRecord record = new SourceRecord(
				null,
				null,
				topic,
				VALUE_SCHEMA,
				getRandomIntFromApi()
		);
		return Collections.singletonList(record);
	}
	
	private Integer getRandomIntFromApi() {
		HttpGet httpGet = new HttpGet("http://" + apiUrl + "/random/int");
	    
	    try {
	    	CloseableHttpResponse response = httpClient.execute(httpGet);
	        return Integer.parseInt(EntityUtils.toString(response.getEntity()));
	    } catch (ClientProtocolException e) {
	        logger.error("Error consuming GET /random/int: {}", e);
	    } catch (IOException e) {
	        logger.error("IO Error: {}", e);
	    }
	    return null;
	}
}
