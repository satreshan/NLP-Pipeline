package com.sap.shp.nlp.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity 
@IdClass(PipelineActionsId.class)
@Table(name = "PipelineActions")
public class PipelineActions implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "pipelineId")
	private int pipelineId;
	
	@Id
	@Column(name = "actionName")
	private String actionName;
	
	@Column(name = "actionUrl")
	private String actionUrl;
	
	@Id
	@Column(name = "status")
	private String status;
	
	@Column(name = "nextAction")
	private String nextAction;
	
	@Column(name = "createdAt")
	private Timestamp createdAt;
	
	/*
	 * Getters and setter
	 */
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}


	public int getPipelineId() {
		return pipelineId;
	}
	public void setPipelineId(int pipelineId) {
		this.pipelineId = pipelineId;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNextAction() {
		return nextAction;
	}
	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
