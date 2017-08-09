package pipelineexecuter;

import org.json.JSONException;
import org.json.JSONObject;

import statemanager.StateManager;
import db.RedisConnection;
import kafkaapi.MyKafkaImpl;
import kafkaapi.ProcessMessageCallback;

public class PipelineConsumer implements ProcessMessageCallback{
	
	private PipelineExecuter executer;
	
	public PipelineConsumer(PipelineExecuter executer){
		this.executer = executer;
	}
	public boolean processMessage(String message) {
		// TODO Auto-generated method stub
		
		RedisConnection redis = null;
		try {
			JSONObject messageJson = new JSONObject(message);
			String key = messageJson.getString("id");
			redis = new RedisConnection("localhost");
			JSONObject latestState = executer.getStateManager().readLatestState(redis, key);
			
			if((latestState != null) && (latestState.getString("status").toLowerCase().equals("running"))){
				//Update the status as completed for latest state
				//executer.getStateManager().updateStatusMessage(redis, key, latestState.getString("action"), "completed");
				JSONObject nextAction = executer.getPipelineConfiguration().getNextAction(latestState.getString("action"));
				executer.getStateManager().updateStatusMessage(redis, key, latestState.getString("action"), "completed", messageJson.getString("content"));
				if(nextAction != null){
					//get the topic of next action
					String topic = nextAction.getString("topic");
					executer.getKafkaUtility().produceMessage(new MyKafkaImpl(), topic, message);
					//Update the status as running for next action
					executer.getStateManager().updateStatusMessage(redis, key, nextAction.getString("name"), "running",messageJson.getString("content"));					
				}
				else
				{
					//Update the status as completed
					executer.getStateManager().updateStatusMessage(redis, key, "Finalize", "completed",messageJson.getString("content"));
					System.out.println("Full status report: " + executer.getStateManager().readStatusMessage(redis, key).toString());
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (redis != null){
				redis.close();
			}
		}
		return true;
	}

}
