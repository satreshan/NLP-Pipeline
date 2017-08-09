package kafkaapi;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;

public interface MyKafka {
	
	KafkaConsumer<String, String> myKafkaConsumer();
	Producer<String, String> myKafkaProducer();
}
