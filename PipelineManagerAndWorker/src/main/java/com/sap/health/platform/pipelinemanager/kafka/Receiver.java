package com.sap.health.platform.pipelinemanager.kafka;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import com.sap.health.platform.pipelinemanager.model.Message;
import com.sap.health.platform.pipelinemanager.repository.MessageRepository;
import com.sap.health.platform.pipelinemanager.worker.Worker;

public class Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	
	@Autowired
	Worker worker;
	
	@KafkaListener(topics = "nlp")
	public void receive(Message message) {
		LOGGER.info("received payload='{}'", message);
		worker.executePipeline(message);
}
}
