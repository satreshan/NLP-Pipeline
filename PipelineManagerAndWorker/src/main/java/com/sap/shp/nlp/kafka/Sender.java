package com.sap.shp.nlp.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sap.shp.nlp.model.Message;

@Service
public class Sender {
	@Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    
    public void send(String topic, Message message){
        kafkaTemplate.send(topic, message);
        System.out.println("Message sent: "+ message.toString());
    }
 
}
