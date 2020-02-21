package com.dataAlliance.hspark.kafka.partitioner;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

public class RoundRobinPartitioner implements Partitioner {
	private AtomicInteger n = new AtomicInteger(0);
	
	@Override
	public void configure(Map<String, ?> configs) {
		
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		List<PartitionInfo> partition = cluster.partitionsForTopic(topic);
		int i = n.getAndIncrement();
		
		if (i == Integer.MAX_VALUE) {
			n.set(0);
			return 0;
		}
		return i % partition.size();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
