package com.example.IoT.Device.Management.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Properties;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {


    private String kafkaServer="9092";


    private String kafkaGroupId="mygrop";

    @Bean
    public ConsumerFactory<String, String> consumerConfig() {

        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        props.put("message.assembler.buffer.capacity", 33554432);
        props.put("max.tracked.messages.per.partition", 24);
        props.put("exception.on.message.dropped", true);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put("segment.deserializer.class", DefaultSegmentDeserializer.class.getName());

        return new DefaultKafkaConsumerFactory(props, null, new StringDeserializer());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> listener = new ConcurrentKafkaListenerContainerFactory<>();
        listener.setConsumerFactory(consumerConfig());
        return listener;
    }
}