package com.dataAlliance.hspark.kafka.connect.source.util;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;;

public class RandomIntHttpClient {
	public static CloseableHttpClient httpClient = HttpClients.createDefault();
}
