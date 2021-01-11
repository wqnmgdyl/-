package com.kh.kafkastudy;

import org.apache.kafka.clients.consumer.*;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author han.ke
 */
public class MyConsumer {
    private static KafkaConsumer<String, String> consumer;
    private static Properties properties;

    static {
        properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "KafkaStudy");
    }

    private static void generalConsumeMessageAutoCommit() {
        properties.put("enable.auto.commit", true);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton("kafka-study-x"));

        try {
            while (true) {
                boolean flag = true;
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "topic = %s, partition = %s, key = %s, value = %s",
                            record.topic(), record.partition(),
                            record.key(), record.value()
                    ));
                    if (record.value().equals("done")) {
                        flag = false;
                    }
                }
                if (!flag) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }

    private static void generalConsumerMessageSyncCommit() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("kafka-study-x"));

        while (true) {
            boolean flag = true;
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format(
                        "topic = %s, partition = %s, key = %s, value = %s",
                        record.topic(), record.partition(),
                        record.key(), record.value()
                ));
                if (record.value().equals("done")) {
                    flag = false;
                }
            }
            try {
                consumer.commitSync();
            } catch (CommitFailedException e) {
                System.out.println("commit failed error: " + e.getMessage());
            }
            if (!flag) {
                break;
            }
        }
    }

    private static void generalConsumeMessageAsyncCommit() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("kafka-study-x"));

        while (true) {
            boolean flag = true;
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format(
                        "topic = %s, partition = %s, key = %s, value = %s",
                        record.topic(), record.partition(),
                        record.key(), record.value()
                ));
                if (record.value().equals("done")) {
                    flag = false;
                }
            }

            //commit A,offset 2000
            //commit B,offset 3000
            consumer.commitAsync();

            if (!flag) {
                break;
            }
        }
    }

    private static void generalConsumeMessageAsync() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("kafka-study-x"));

        while (true) {
            boolean flag = true;
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format(
                        "topic = %s, partition = %s, key = %s, value = %s",
                        record.topic(), record.partition(),
                        record.key(), record.value()
                ));
                if (record.value().equals("done")) {
                    flag = false;
                }
            }

            consumer.commitAsync((map, e) -> {
                if (e != null) {
                    System.out.println("commit failed for offsets: " + e.getMessage());
                }
            });

            if (!flag) {
                break;
            }
        }
    }

    private static void mixSyncAndAsyncCommit() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("kafka-study-x"));

        try {
            while (true) {
                boolean flag = true;
                ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "topic = %s, partition = %s, key = %s, value = %s",
                            record.topic(), record.partition(),
                            record.key(), record.value()
                    ));
                    if (record.value().equals("done")) {
                        flag = false;
                    }
                }
                consumer.commitAsync();
                if (!flag) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("commit async error: " + e.getMessage());
        } finally {
            try {
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }
    }

    public static void main(String[] args) {
        generalConsumeMessageAutoCommit();
    }
}
