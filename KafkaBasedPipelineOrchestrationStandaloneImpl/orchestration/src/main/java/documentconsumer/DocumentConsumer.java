package documentconsumer;

import org.json.JSONObject;

import pipelineexecuter.PipelineExecuter;
import db.RedisConnection;
import resolve.DocumentTypeResolver;
import kafkaapi.MyKafkaImpl;
import kafkaapi.ProcessMessageCallback;

public class DocumentConsumer implements ProcessMessageCallback{
	
	public boolean processMessage(String message) {
		RedisConnection redis = new RedisConnection("localhost");
		PipelineExecuter executer = null;
		// TODO Auto-generated method stub
		try {
			DocumentTypeResolver resolver = new DocumentTypeResolver();
			String msg = resolver.processMessage(redis,message);
			String pipeline = new JSONObject(msg).getString("pipeline");
			executer = new PipelineExecuter(pipeline);
			//Send the resolved msg to executer
			if (executer.invokeExecuter(redis, new MyKafkaImpl(),msg))
				System.out.println("Message is forwarded to executer.");	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			redis.close();
		}
		return true;
	}

}
