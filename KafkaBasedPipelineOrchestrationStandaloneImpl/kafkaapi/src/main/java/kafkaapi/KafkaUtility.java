package kafkaapi;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

import com.google.common.io.Resources;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import kafka.admin.AdminUtils;
import kafka.common.TopicExistsException;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

public class KafkaUtility {

	// Create Kafka topic
	public boolean createTopic(String zookeeperHosts, String topicName,
			int noOfPartitions, int noOfReplication) {
		ZkClient zkClient = null;
		ZkUtils zkUtils = null;
		try {
			// String zookeeperHosts = "localhost:2181"; // If multiple
			// zookeeper then -> String zookeeperHosts =
			// "192.168.20.1:2181,192.168.20.2:2181";
			int sessionTimeOutInMs = 15 * 1000; // 15 secs
			int connectionTimeOutInMs = 10 * 1000; // 10 secs

			zkClient = new ZkClient(zookeeperHosts, sessionTimeOutInMs,
					connectionTimeOutInMs, ZKStringSerializer$.MODULE$);
			zkUtils = new ZkUtils(zkClient, new ZkConnection(zookeeperHosts),
					false);

			if (topicName == null) {
				// topicName = "testTopic";
				System.out
						.println("Please specify the name of the Topic to be created.");
				return false;
			}

			Properties topicConfiguration = new Properties();

			AdminUtils.createTopic(zkUtils, topicName, noOfPartitions,
					noOfReplication, topicConfiguration);

		} catch (TopicExistsException e){
			System.out.println(topicName + " topic already exists.");
			return false;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (zkClient != null) {
				zkClient.close();
			}
		}
		return true;
	}

	// Register callback
	private boolean registerProcessMessageCallback(String message,
			ProcessMessageCallback callback) {
		return callback.processMessage(message);
	}

	// Consume message from Kafka topic
	public void consumeMessage(MyKafka kafka, String topicName,
			ProcessMessageCallback callback) throws Exception {
		if (topicName == null) {
			System.out.println("Provide topic name");
			return;
		}
		KafkaConsumer<String, String> consumer = kafka.myKafkaConsumer();

		// Kafka Consumer subscribes list of topics here.
		consumer.subscribe(Arrays.asList(topicName));

		// print the topic name
		System.out.println("Subscribed to topic " + topicName);
		// int i = 0;

		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				// print the offset,key and value for the consumer records.
				// System.out.printf("offset = %d, key = %s, value = %s\n",
				// record.offset(), record.key(), record.value());
				if (registerProcessMessageCallback(record.value(), callback)) {
					System.out
							.println("Message processed successfully on topic "
									+ topicName);
				}else{
					System.out.println("Failed to process message from topic "+ topicName);
				}
				
			}
		}
	}

	// Produce message on kafka topic
	public boolean produceMessage(MyKafka kafka, String topicName,
			String message) {
		Producer<String, String> producer = null;
		try {
			
			// Check arguments null value
			if (topicName == null) {
				System.out
						.println("Please specify the topic name to produce the message.");
				return false;
			}
			producer = kafka.myKafkaProducer();

			producer.send(new ProducerRecord<String, String>(topicName, message));
			System.out.println("Message sent successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (producer != null)
				producer.close();
		}
		
		return true;
	}
}
