package com.sap.health.platform.pipelinemanager.kafka;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.sap.health.platform.pipelinemanager.model.Message;

@Service
public class Sender {
	@Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    
    public boolean send(String topic, Message message){
        try {
			SendResult<String, Message> result = kafkaTemplate.send(topic, message).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			return false;
		}
        System.out.println("Message sent: "+ message.toString());
        return true;
    }
 
}
