package kafkaapi;

public class Run implements ProcessMessageCallback{

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KafkaUtility kafka = new KafkaUtility();
		ProcessMessageCallback callback = new Run();
		MyKafka mykafka = new MyKafkaImpl();
		try {
			kafka.consumeMessage(mykafka,"medication",callback);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean processMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println("Message received: "+message);
		return true;
	}

}
