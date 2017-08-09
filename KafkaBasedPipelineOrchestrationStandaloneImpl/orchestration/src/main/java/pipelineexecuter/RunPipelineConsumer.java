package pipelineexecuter;

import kafkaapi.KafkaUtility;
import kafkaapi.MyKafka;
import kafkaapi.MyKafkaImpl;
import documentconsumer.DocumentConsumer;

public class RunPipelineConsumer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				KafkaUtility kafka = new KafkaUtility();
				PipelineExecuter executer = new PipelineExecuter("samplepipeline");
				MyKafka myKafka = new MyKafkaImpl();
				try {
					//How to get pipelineexecuter?
					//kafka.consumeMessage(myKafka, "sample", new PipelineConsumer(new PipelineExecuter("samplepipeline")));
					executer.executePipeline(myKafka);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

}
