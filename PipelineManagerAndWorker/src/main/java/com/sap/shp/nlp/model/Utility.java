package com.sap.shp.nlp.model;

import java.util.Date;
import java.sql.Timestamp;

public class Utility {

	public Timestamp getTimestamp(){
		Date date = new Date();
		return new Timestamp(date.getTime());
	}
	
	public PipelineActions getInitialPipelineAction(int id){
		  PipelineActions initialAction = new PipelineActions();
		  initialAction.setPipelineId(id);
		  initialAction.setActionName("Initialize");
		  initialAction.setActionUrl("dummyUrl");
		  initialAction.setStatus("Initialized");
		  initialAction.setNextAction("Finalize");
		  initialAction.setCreatedAt(getTimestamp());
		  return initialAction;
	}
	
	public PipelineActions getFinalPipelineAction(boolean isErr,int id){
		PipelineActions finalAction = new PipelineActions();
		finalAction.setPipelineId(id);
		finalAction.setActionName("Finalize");
		finalAction.setActionUrl("dummyUrl");
		if(isErr)
			finalAction.setStatus("FinalizedWithError");
		else 
			finalAction.setStatus("Finalized");
		finalAction.setNextAction("Finalize");
		finalAction.setCreatedAt(getTimestamp());
		return finalAction;
	}
	
	public PipelineActions getPipelineAction(boolean isErr,PipelineAction action,int id){
		PipelineActions pipelineAction = new PipelineActions();
		pipelineAction.setPipelineId(id);
		pipelineAction.setActionName(action.getActionName());
		pipelineAction.setActionUrl(action.getActionUrl());
		if(isErr)
			pipelineAction.setStatus("Error");
		else
			pipelineAction.setStatus("Success");
		pipelineAction.setNextAction("Finalize");
		pipelineAction.setCreatedAt(getTimestamp());
		return pipelineAction;
	}
}
