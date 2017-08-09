package statemanager;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import db.RedisConnection;

public class StateManager {
	
	private JSONObject generateState(String action, String status, String content){
		JSONObject state = new JSONObject();
		state.put("action", action);
		state.put("status", status);
		state.put("timestamp", new Date().toString());
		state.put("content", content);
		return state;
	}
	//Initialize Status message
	public boolean initializeStatusMessage(String key, String action, String status, String content){
		RedisConnection redis = new RedisConnection("localhost");
		JSONArray message = new JSONArray();
		JSONObject state = generateState(action, status,content);
		message.put(state);
		redis.pushValue(key, message.toString());
		redis.close();
		return true;
	}
	
	//Update Status message
	public boolean updateStatusMessage(RedisConnection redis, String key, String action, String status, String content){
		String message = redis.popValue(key);
		JSONArray messageJson = new JSONArray(message);
		JSONObject state = generateState(action, status, content);
		messageJson.put(state);
		redis.pushValue(key, messageJson.toString());
		return true;
	}
	
	//Read Status message
	public JSONArray readStatusMessage(RedisConnection redis, String key){
		String message = redis.popValue(key);
		JSONArray messageJson = new JSONArray(message);
		return messageJson;
	}
	
	//Read latest state
	public JSONObject readLatestState(RedisConnection redis, String key){
		JSONArray message = readStatusMessage(redis,key);
		int length = message.length();
		if (length > 0){
			JSONObject latestState = message.getJSONObject(message.length() - 1);
			return latestState;
		}
		else
			return null;		
	}
}
