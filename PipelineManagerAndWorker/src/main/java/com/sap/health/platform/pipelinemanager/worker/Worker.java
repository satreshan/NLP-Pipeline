package com.sap.health.platform.pipelinemanager.worker;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.sap.health.platform.pipelinemanager.kafka.Receiver;
import com.sap.health.platform.pipelinemanager.model.Message;
import com.sap.health.platform.pipelinemanager.model.PipelineActions;
import com.sap.health.platform.pipelinemanager.model.PipelineConfiguration;
import com.sap.health.platform.pipelinemanager.model.PipelineStatus;
import com.sap.health.platform.pipelinemanager.model.PipelineStatusId;
import com.sap.health.platform.pipelinemanager.service.PipelineService;
import com.sap.health.platform.pipelinemanager.service.PipelineServiceImpl;
import com.sap.health.platform.pipelinemanager.worker.service.WorkerService;

@Service
public class Worker {
	
	//private List<PipelineActions> configuration;
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	
	@Autowired
	Action action;
	
	@Autowired
	WorkerService workerService;		

	public boolean executePipeline(Message message){
		ResponseEntity<String> response;
		//Validate message
		
		PipelineStatus currentStatus = workerService.getPipelineStatus(message.getMetadata().getJob().getJobId(), "Initialize");
		
		//Execute actions
		PipelineActions nextAction = workerService.getNextAction(currentStatus);
		while(nextAction != null && !(nextAction.getActionName().equals("Finalize"))){
			workerService.updatePipelineActionStatus(message.getMetadata().getJob(), nextAction.getActionName(), "InProcess");
			response = action.executePostAction(nextAction.getActionUrl(), message.getContent());
			if(response.getStatusCodeValue() == 200){
				message.setContent(response.getBody());
				workerService.updatePipelineActionStatus(message.getMetadata().getJob(), nextAction.getActionName(), "Success");
			}
			else
				workerService.updatePipelineActionStatus(message.getMetadata().getJob(), nextAction.getActionName(), "Error");
			currentStatus = workerService.getPipelineStatus(message.getMetadata().getJob().getJobId(), nextAction.getActionName());
			nextAction = workerService.getNextAction(currentStatus);
		}
		//Write status back
		workerService.updatePipelineActionStatus(message.getMetadata().getJob(), nextAction.getActionName(), nextAction.getStatus());
		return true;
	}

}
