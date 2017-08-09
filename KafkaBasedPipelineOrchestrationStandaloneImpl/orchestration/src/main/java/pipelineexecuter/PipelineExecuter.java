package pipelineexecuter;

import kafkaapi.KafkaUtility;
import kafkaapi.MyKafka;

import org.json.JSONObject;

import db.RedisConnection;
import pipelineconfiguration.PipelineConfiguration;
import statemanager.StateManager;


public class PipelineExecuter {
	private StateManager stateMgr;
	private KafkaUtility kafka;
	private PipelineConfiguration pipelineConfig;
	
	public PipelineExecuter(String pipelineName) {
		stateMgr = new StateManager();
		kafka = new KafkaUtility();
		pipelineConfig = new PipelineConfiguration(pipelineName);
		// Create executer topic
		//kafka.createTopic("localhost:2181", pipelineConfig.getExecuterTopic(), 1, 1);
	}

	public StateManager getStateManager(){
		return stateMgr;
	}
	
	public KafkaUtility getKafkaUtility(){
		return kafka;
	}
	
	public PipelineConfiguration getPipelineConfiguration(){
		return pipelineConfig;
	}
	
	public boolean invokeExecuter(RedisConnection redis, MyKafka myKafka,
			String message) {
		// Finds pipeline configuration and submit the messsage to first
		// action's topic
		JSONObject messageJson = new JSONObject(message);
		String DocId = messageJson.getString("id");
		messageJson.put("executertopic", getPipelineConfiguration().getExecuterTopic());
		System.out.println("Executer topic:"+ getPipelineConfiguration().getExecuterTopic());
		JSONObject initialAction = pipelineConfig.getInitialAction();
		stateMgr.initializeStatusMessage(DocId,"Initialize", "completed",messageJson.getString("content"));
		//stateMgr.updateStatusMessage(redis, DocId, initialAction.getString("name"), "running");
		// Produce the message on resp kafka
		if (kafka.produceMessage(myKafka, initialAction.getString("topic"), messageJson.toString()))
			System.out.println("Message is forwarded to "+initialAction.getString("topic"));;

		// Upadate initial status
		stateMgr.updateStatusMessage(redis, DocId, initialAction.getString("name"), "running", messageJson.getString("content"));
		return true;
	}

	public boolean executePipeline(MyKafka myKafka) {
		// Consumes message from executer topic and forward to respective
		// actions topic
		try {
			//pipelineConfig.getExecuterTopic()
			kafka.consumeMessage(myKafka, pipelineConfig.getExecuterTopic() , new PipelineConsumer(this));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
