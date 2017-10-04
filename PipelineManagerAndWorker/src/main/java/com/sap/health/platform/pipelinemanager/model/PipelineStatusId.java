package com.sap.health.platform.pipelinemanager.model;

import java.io.Serializable;

public class PipelineStatusId implements Serializable{
	
	//Override hashCode() and equals() method
	private int jobId;
	private int pipelineId;
	private String documentId;
	private String action;
	
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public int getPipelineId() {
		return pipelineId;
	}
	public void setPipelineId(int pipelineId) {
		this.pipelineId = pipelineId;
	}

	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getJobId() + " " + this.getPipelineId()+ " " + this.getDocumentId() + " " +this.getAction();
	}
	
	
}
