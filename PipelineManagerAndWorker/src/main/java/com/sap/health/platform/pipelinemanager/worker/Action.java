package com.sap.health.platform.pipelinemanager.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.sap.health.platform.pipelinemanager.kafka.Receiver;
import com.sap.health.platform.pipelinemanager.model.Message;

@Service
public class Action{

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	@Autowired
	private RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public ResponseEntity<String> executePostAction(String url, Object requestBody){
		String response  = null;
		try {
			response = restTemplate.postForObject(url, requestBody, String.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.valueOf(500)).body("Time Out");
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.valueOf(500)).body("Time Out");
		}
		LOGGER.info("Processed message: " + response);
		return ResponseEntity.ok().body(response);
	}
	
}
