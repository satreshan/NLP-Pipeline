package pipelineexecuter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import kafkaapi.KafkaUtility;
import kafkaapi.MyKafka;
import kafkaapi.MyKafkaImpl;

import com.google.common.io.CharStreams;
import com.google.common.io.Resources;

public class ProduceMessage {
	
	public void produce(){
		InputStream message;
		KafkaUtility kafka = new KafkaUtility();
		MyKafka myKafka = new MyKafkaImpl();
		try {
			message = Resources.getResource("message.json").openStream();
			String messageStr = CharStreams.toString(new InputStreamReader(message));
			kafka.produceMessage(myKafka, "medication", messageStr);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProduceMessage producer  = new ProduceMessage();
		producer.produce();
	}

}
