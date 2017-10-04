package com.sap.health.platform.pipelinemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.health.platform.pipelinemanager.service.ActionService;

@RestController
@RequestMapping(value = "/pipeline/action")
public class ActionController {

	@Autowired
	ActionService actionService;
	
	@PostMapping(value = "/action1", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> processAction1(@RequestBody String message){
		return ResponseEntity.ok().body(actionService.processAction1(message));
	}
	
	@PostMapping(value = "/action2", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> processAction2(@RequestBody String message){
		return ResponseEntity.ok().body(actionService.processAction2(message));
	}
	
	@PostMapping(value = "/action3", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> processAction3(@RequestBody String message){
		return ResponseEntity.ok().body(actionService.processAction3(message));
	}	
	
}
