package com.sap.shp.nlp.kafka;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.sap.shp.nlp.model.KafkaMessage;
import com.sap.shp.nlp.model.Message;
import com.sap.shp.nlp.repository.MessageRepository;

public class Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	@Autowired
	MessageRepository msgRepository;
	
	@KafkaListener(topics = "nlp")
	public void receive(Message message) {
		LOGGER.info("received payload='{}'", message);	 
		System.out.println("Received Messasge in group foo: " + message.toString());
	}
}
