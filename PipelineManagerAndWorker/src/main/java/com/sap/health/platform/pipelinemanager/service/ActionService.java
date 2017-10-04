package com.sap.health.platform.pipelinemanager.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sap.health.platform.pipelinemanager.model.KafkaMessage;
import com.sap.health.platform.pipelinemanager.repository.MessageRepository;

@Service("actionService")
public class ActionService {

	MessageRepository messageRepository;
	
	@Inject
	public ActionService(MessageRepository messageRepository){
		this.messageRepository = messageRepository;
	}
	
	public String processAction1(String message){
		message = message + " Processed message in action1. ";
		return message;
	}
	
	public String processAction2(String message){
		message = message + " Processed message in action2. ";
		return message;
	}
	
	public String processAction3(String message){
		KafkaMessage msg = new KafkaMessage();
		msg.setMsg(message);
		messageRepository.save(msg);
		return "Message persisted.";
	}
}
