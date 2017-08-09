package pipelineexecuter;

import kafkaapi.KafkaUtility;
import kafkaapi.MyKafka;
import kafkaapi.MyKafkaImpl;
import actions.ChangeContent;

public class RunChangeConsumer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KafkaUtility kafka = new KafkaUtility();
		MyKafka myKafka = new MyKafkaImpl();
		try {
			kafka.consumeMessage(myKafka, "step2topic", new ChangeContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
