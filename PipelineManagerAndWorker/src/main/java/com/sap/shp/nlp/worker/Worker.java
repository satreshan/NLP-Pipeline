package com.sap.shp.nlp.worker;

import org.springframework.beans.factory.annotation.Autowired;

import com.sap.shp.nlp.model.Message;
import com.sap.shp.nlp.model.PipelineConfiguration;
import com.sap.shp.nlp.worker.service.WorkerService;

public class Worker {
	private PipelineConfiguration configuration;
	private Message message;
		
	public Worker(Message message) {
		super();
		// TODO Auto-generated constructor stub
		this.message = message;
		this.configuration = WorkerService.getPipelineConfiguration(message.getPipelineId());
	}
	
	private boolean registerExecuteActionCallback(Message message, ExecuteActionsCallback callback){
		return callback.executeAction(message);
	}
	
	public boolean executePipeline(){
		//Call pipeline actions
		return true;
	}
	
}
