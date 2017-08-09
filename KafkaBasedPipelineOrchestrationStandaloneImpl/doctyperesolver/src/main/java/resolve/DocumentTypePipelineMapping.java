package resolve;

import db.RedisConnection;

public class DocumentTypePipelineMapping {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RedisConnection redis = null;
		//Map the document type amd pipeline
		try {
			redis = new RedisConnection("localhost");
			redis.pushValue("DoctorLetter", "samplepipeline");
			System.out.println("Mapped DoctorLetter to samplepipeline successfully.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (redis != null)
				redis.close();
		}
	}

}
