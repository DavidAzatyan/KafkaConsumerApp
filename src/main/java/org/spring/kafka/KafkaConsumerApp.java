package org.spring.kafka;

import org.spring.kafka.conf.DataSourceConfig;
import org.spring.kafka.conf.KafkaConsumerConfig;
import org.spring.kafka.consumer.KafkaMessageConsumer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class KafkaConsumerApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                KafkaConsumerConfig.class, DataSourceConfig.class, KafkaMessageConsumer.class);
    }
}
