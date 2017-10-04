package com.sap.shp.nlp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;


@Entity
@Table(name = "Pipeline")
public class Pipeline implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)	 
	@Column(name = "pipelineId")
	private int pipelineId;
	
	@NotNull
	@Column(name = "pipelineName")
	private String pipelineName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "createdAt")
	private Timestamp createdAt;
	
	/*
	 * No argument constructor can't be defined, already defined by repository.
	 * Fails if constructor with arguments are defined.
	 */

/*	public Pipeline(int pipelineId, String pipelineName, Timestamp createdAt) {
		this.pipelineId = pipelineId;
		this.pipelineName = pipelineName;
		this.createdAt = createdAt;
	}
	
	public Pipeline(int pipelineId, String pipelineName) {
		this.pipelineId = pipelineId;
		this.pipelineName = pipelineName;
		this.createdAt = null;
	}*/

	public int getPipelineId() {
		return pipelineId;
	}
	public void setPipelineId(int pipelineId) {
		this.pipelineId = pipelineId;
	}
	public String getPipelineName() {
		return pipelineName;
	}
	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
