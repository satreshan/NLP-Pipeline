package com.sap.shp.nlp.model;

import java.io.Serializable;

public class PipelineStatusId implements Serializable{
	
	//Override hashCode() and equals() method
	private int jobId;
	private int pipelineId;
	private String action;
	private String status;
}
