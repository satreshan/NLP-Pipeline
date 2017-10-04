package com.sap.health.platform.pipelinemanager.model;

import java.io.Serializable;

public class PipelineActionsId implements Serializable{

	//overirde hashcode() and equals() methods and getter setters
	int pipelineId;
	String actionName;
	String status;
}
