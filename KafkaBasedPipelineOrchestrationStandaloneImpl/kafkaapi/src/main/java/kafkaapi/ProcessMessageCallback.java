package kafkaapi;

public interface ProcessMessageCallback {
	boolean processMessage(String message);
}
