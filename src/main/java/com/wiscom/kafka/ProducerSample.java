package com.wiscom.kafka;

/**
 * Created by cjf on 2014/12/19.
 */

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class ProducerSample {
    public static void main(String[] args) {
        ProducerSample ps = new ProducerSample();

        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "com.wiscom.kafka.SimplePartitioner");
        //props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
        KeyedMessage<String, String> data = new KeyedMessage<String, String>("test", "test-message2");
        producer.send(data);
        producer.close();
    }
}
