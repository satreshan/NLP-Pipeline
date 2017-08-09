package resolve;

import redis.clients.jedis.Jedis;
import db.RedisConnection;

import org.json.*;


public class DocumentTypeResolver {
	
	//Get the document Type pipeline mapping from redis
	private String getPipeline(RedisConnection redis, String docType){
		return redis.popValue(docType);
	}

	public String processMessage(RedisConnection redis, String message) {
		// TODO Auto-generated method stub
		//Read message json
		try {
			JSONObject obj = new JSONObject(message);
			String docType = obj.getString("type");
			String pipeline = getPipeline(redis, docType);
			obj.put("pipeline", pipeline);
			message = obj.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
}
