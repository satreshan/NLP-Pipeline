package com.sap.shp.nlp.service;

import java.util.List;

import com.sap.shp.nlp.model.Job;
import com.sap.shp.nlp.model.Message;
import com.sap.shp.nlp.model.Pipeline;
import com.sap.shp.nlp.model.PipelineActions;
import com.sap.shp.nlp.model.PipelineConfiguration;

public interface PipelineService {

	Pipeline createPipeline(PipelineConfiguration pipeline);
	Pipeline updatePipeline(PipelineConfiguration pipeline, int id);
	boolean deletePipeline(int id);
	List<PipelineActions> getPipelineActionsById(int id);
	Pipeline getPipelineMetadataById(int id);
	List<Pipeline> getPipelineMetadataByName(String pipelineName);
	Job executePipeline(int pipelineId, Message message);
	Job getJobDetails(int jobId);
}
