package kafkaapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import com.google.common.io.Resources;

public class MyKafkaImpl implements MyKafka{

	@Override
	public KafkaConsumer<String, String> myKafkaConsumer() {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		KafkaConsumer<String, String> consumer = null;
		try (InputStream properties = Resources.getResource("consumer.props")
				.openStream()) {
			props.load(properties);
			consumer = new KafkaConsumer<String, String>(props);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		return consumer;
	}

	@Override
	public Producer<String, String> myKafkaProducer() {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		Producer<String, String> producer = null;
		try (InputStream properties = Resources.getResource("producer.props")
				.openStream()) {
			props.load(properties);
			//Producer<String, String> producer = new KafkaProducer<String, String>(props);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		producer = new KafkaProducer<String, String>(props);
		return producer;
	}

}
