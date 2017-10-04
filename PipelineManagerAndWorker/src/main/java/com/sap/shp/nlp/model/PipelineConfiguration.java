package com.sap.shp.nlp.model;

import java.util.List;

public class PipelineConfiguration {

	private Pipeline metadata;
	private List<PipelineAction> actions;
	

	public Pipeline getMetadata() {
		return metadata;
	}
	public void setMetadata(Pipeline metadata) {
		this.metadata = metadata;
	}
	public List<PipelineAction> getActions() {
		return actions;
	}
	public void setActions(List<PipelineAction> actions) {
		this.actions = actions;
	}
	
	
}
