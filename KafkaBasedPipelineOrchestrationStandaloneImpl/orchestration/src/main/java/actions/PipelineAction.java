package actions;

import kafkaapi.MyKafka;
import kafkaapi.ProcessMessageCallback;

public interface PipelineAction extends ProcessMessageCallback {
	boolean executeAction(MyKafka kafka);
}
