package com.sap.shp.nlp.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "PipelineStatus")
@Data
@RequiredArgsConstructor
@IdClass(PipelineStatusId.class)
public class PipelineStatus {
	@Id
	@Column(name = "jobId")
	private int jobId;
	
	@Id
	@Column(name = "pipelineId")
	private int pipelineId;
	
	@Id
	@Column(name = "action")
	private String action;
	
	@Id
	@Column(name = "status")
	private String status;
	
	@Column(name = "createdAt")
	private Timestamp createdAt;
	
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}
