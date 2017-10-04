package com.sap.health.platform.pipelinemanager.worker.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.sym.Name;
import com.sap.health.platform.pipelinemanager.kafka.Receiver;
import com.sap.health.platform.pipelinemanager.model.Job;
import com.sap.health.platform.pipelinemanager.model.PipelineActions;
import com.sap.health.platform.pipelinemanager.model.PipelineConfiguration;
import com.sap.health.platform.pipelinemanager.model.PipelineStatus;
import com.sap.health.platform.pipelinemanager.model.PipelineStatusId;
import com.sap.health.platform.pipelinemanager.service.PipelineService;

@Service("workerService")
public class WorkerService {
	
	@Autowired
	PipelineService service;

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkerService.class);
	
	public PipelineStatus getPipelineStatus(int jobId, String action) {
		// TODO Auto-generated method stub		
		return service.getPipelineStatus(jobId, action);
	}

	public List<PipelineActions> getPipelineActions(int pipelineId){
		// TODO Auto-generated method stub
		List<PipelineActions> actions = null;
		actions = service.getPipelineActionsById(pipelineId);
		return actions;
	}
	
	public PipelineActions getNextAction(PipelineStatus status){
		// TODO Auto-generated method stub
		PipelineActions currentAction = service.getPipelineActionsByPipelineIdAndActionAndStatus(status);
		status.setAction(currentAction.getNextAction());
		if(status.getAction().equals("Finalize"))
		{
			if(status.getStatus().equals("Error"))
				status.setStatus("FinalizedWithError");
			else
				status.setStatus("Finalized");
		}
		else if(status.getStatus().equals("Error"))
		{
			status.setStatus("Error");
		}else{
			status.setStatus("Success");
		}			
		currentAction = service.getPipelineActionsByPipelineIdAndActionAndStatus(status);
		return currentAction;
	}
	
	public boolean updatePipelineActionStatus(Job job, String action, String status){
		return service.updatePipelineStatus(job, action, status);
	}

}
