package com.sap.health.platform.pipelinemanager.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.sap.health.platform.pipelinemanager.model.Message;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
 
	@Value("${spring.kafka.bootstrap-servers}")
	String bootstrap_server;

    @Bean
    public ConsumerFactory<String, Message> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
          ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          bootstrap_server);
        props.put(
          ConsumerConfig.GROUP_ID_CONFIG, 
          "nlp");
        props.put(
          ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 
          StringDeserializer.class);
        props.put(
          ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
          JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props,new StringDeserializer(), 
        	      new JsonDeserializer<>(Message.class));
    }
 
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message> 
      kafkaListenerContainerFactory() {
    
        ConcurrentKafkaListenerContainerFactory<String, Message> factory
          = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
    
    @Bean
    public Receiver receiver() {
      return new Receiver();
    }
}