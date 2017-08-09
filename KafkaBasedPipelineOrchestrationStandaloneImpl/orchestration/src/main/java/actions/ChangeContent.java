package actions;

import org.json.JSONException;
import org.json.JSONObject;

import kafkaapi.KafkaUtility;
import kafkaapi.MyKafka;
import kafkaapi.MyKafkaImpl;

public class ChangeContent implements PipelineAction {
	KafkaUtility utility;
	
	public ChangeContent(){
		 utility = new KafkaUtility();
	}
	public boolean processMessage(String message) {
		// TODO Auto-generated method stub
		try {
			JSONObject messageJson = new JSONObject(message);
			String content = messageJson.getString("content") + " --> CHP";
			messageJson.remove("content");
			messageJson.put("content", content);
			utility.produceMessage(new MyKafkaImpl(), messageJson.getString("executertopic"), messageJson.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean executeAction(MyKafka kafka) {
		// TODO Auto-generated method stub
		
		try {
			utility.consumeMessage(kafka, "step2topic", this);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}


}
