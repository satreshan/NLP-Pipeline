package pipelineconfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import kafkaapi.KafkaUtility;

import org.json.JSONArray;
import org.json.JSONObject;


import com.google.common.io.CharStreams;
import com.google.common.io.Resources;

import db.RedisConnection;

public class RegisterPipeline {
	
	private boolean storeConfiguration(RedisConnection redis, String pipelinename,String pipelineconfigjson){
		try{
			redis.pushValue(pipelinename, pipelineconfigjson);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void register(RedisConnection redis, String resourceURL){
		KafkaUtility kafka = new KafkaUtility();
		try {
			InputStream config = Resources.getResource(resourceURL).openStream();
			String configStr = CharStreams.toString(new InputStreamReader(config));
			JSONObject configJson = new JSONObject(configStr);
			String pipelinename = configJson.getJSONObject("metadata").getString("name");
			JSONArray actions = configJson.getJSONArray("actions");
			int i= 0;
			//Create Kafka topics
			for(i=0;i<actions.length();i++){
				String topic = actions.getJSONObject(i).getString("topic");
				//Handle exception if topic already exist.
				if (kafka.createTopic("localhost:2181", topic, 1, 1))
					System.out.println("Topic "+topic+" created successfully.");
			}
			storeConfiguration(redis, pipelinename, configStr);	
			System.out.println("Pipeline "+ pipelinename +" is registered successfully.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RegisterPipeline registry = new RegisterPipeline();
		RedisConnection redis = new RedisConnection("localhost");
		registry.register(redis,"pipelineconfig.json");
	}

}
