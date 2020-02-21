package com.dataAlliance.hspark.kafka.connect.source.util;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class RandomIntSchemas {
    public static final Schema VALUE_SCHEMA = SchemaBuilder.struct().name("random_long")
            .version(1)
            .field("value", Schema.INT64_SCHEMA)
            .build();
}
