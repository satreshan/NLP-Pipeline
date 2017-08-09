package pipelineconfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;
import db.RedisConnection;

/**
 * @author I320726
 *
 */
public class PipelineConfiguration {

	private JSONObject pipelineConfiguration;
	/*
	 * Pipeline configuration
	 * {
	 * "metadata":{"name":"pipelinename",
	 * 				"executertopic": "executertopic"
	 * 			   },
	 * "actions":[
	 * 				{"name":"",
	 * 				 "topic":""	
	 * 				},
	 * 				{
	 * 				 "name":"",
	 * 				 "topic":""
	 * 				}
	 * 			]
	 * }
	 */
	
	public PipelineConfiguration(String pipelineName){
		//Read pipeline configuration from redis
		RedisConnection redis = null;
		try {
			redis = new RedisConnection("localhost");
			String pipelineConfig = redis.popValue(pipelineName);
			pipelineConfiguration = new JSONObject(pipelineConfig); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (redis != null)
				redis.close();
		}
		
	}

	public JSONObject getPipelineConfiguration() {
		return pipelineConfiguration;
	}

	public JSONObject getPipelineMetadata(){
		return getPipelineConfiguration().getJSONObject("metadata");
	}
	
	public String getPipelineName(){
		return getPipelineMetadata().getString("name");
	}
	
	public String getExecuterTopic(){
		return getPipelineMetadata().getString("executertopic");
	}
	

	public JSONArray getActions(){
		return pipelineConfiguration.getJSONArray("actions");
	}
	
	public JSONObject getInitialAction(){
		//int len = pipelineConfiguration.getJSONArray("actions").length();
		return pipelineConfiguration.getJSONArray("actions").getJSONObject(0);
	}
	
	public JSONObject getNextAction(String actionName){
		JSONArray actions = getActions();
		int i = 0;
		while(i < (actions.length()-1)){
			JSONObject action = actions.getJSONObject(i);
			if((action.getString("name").toLowerCase().equals(actionName.toLowerCase())) ){
				return actions.getJSONObject(i+1);
			}
			i++;
		}
		return null;
	}
}
