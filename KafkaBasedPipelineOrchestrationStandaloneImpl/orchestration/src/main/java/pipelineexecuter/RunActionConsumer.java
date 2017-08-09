package pipelineexecuter;

import actions.ProcessContent;
import kafkaapi.KafkaUtility;
import kafkaapi.MyKafka;
import kafkaapi.MyKafkaImpl;
import documentconsumer.DocumentConsumer;

public class RunActionConsumer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KafkaUtility kafka = new KafkaUtility();
		MyKafka myKafka = new MyKafkaImpl();
		try {
			kafka.consumeMessage(myKafka, "step1topic", new ProcessContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
