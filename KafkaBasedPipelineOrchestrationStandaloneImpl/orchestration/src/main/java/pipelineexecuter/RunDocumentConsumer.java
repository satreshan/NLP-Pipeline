package pipelineexecuter;

import documentconsumer.DocumentConsumer;
import kafkaapi.KafkaUtility;
import kafkaapi.MyKafka;
import kafkaapi.MyKafkaImpl;

public class RunDocumentConsumer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KafkaUtility kafka = new KafkaUtility();
		MyKafka myKafka = new MyKafkaImpl();
		try {
			kafka.consumeMessage(myKafka, "medication", new DocumentConsumer());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
