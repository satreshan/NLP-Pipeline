package actions;

import org.json.JSONException;
import org.json.JSONObject;

import kafkaapi.KafkaUtility;
import kafkaapi.MyKafka;
import kafkaapi.MyKafkaImpl;

public class ProcessContent implements PipelineAction {
	KafkaUtility utility;
	
	public ProcessContent(){
		 utility = new KafkaUtility();
	}
	public boolean processMessage(String message) {
		// TODO Auto-generated method stub
		try {
			JSONObject messageJson = new JSONObject(message);
			String content = messageJson.getString("content") + " --> FFH";
			messageJson.remove("content");
			messageJson.put("content", content);
			//messageJson.getString("executertopic")
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
			utility.consumeMessage(kafka, "step1topic", this);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
