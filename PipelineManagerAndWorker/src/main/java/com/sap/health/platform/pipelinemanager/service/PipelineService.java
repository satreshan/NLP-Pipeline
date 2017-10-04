package com.sap.health.platform.pipelinemanager.service;

import java.util.List;

import com.sap.health.platform.pipelinemanager.model.Job;
import com.sap.health.platform.pipelinemanager.model.Message;
import com.sap.health.platform.pipelinemanager.model.Pipeline;
import com.sap.health.platform.pipelinemanager.model.PipelineActions;
import com.sap.health.platform.pipelinemanager.model.PipelineConfiguration;
import com.sap.health.platform.pipelinemanager.model.PipelineStatus;
import com.sap.health.platform.pipelinemanager.model.PipelineStatusId;

public interface PipelineService {

	Pipeline createPipeline(PipelineConfiguration pipeline);
	Pipeline updatePipeline(PipelineConfiguration pipeline, int id);
	boolean deletePipeline(int id);
	List<PipelineActions> getPipelineActionsById(int id);
	PipelineActions getPipelineActionsByPipelineIdAndActionAndStatus(PipelineStatus status);
	Pipeline getPipelineMetadataById(int id);
	List<Pipeline> getPipelineMetadataByName(String pipelineName);
	Job executePipeline(int pipelineId, Message message);
	Job getJobDetails(int jobId);
	List<PipelineStatus> getPipelineStatus(int jobId);
	PipelineStatus getPipelineStatus(int jobId, String action);
	boolean updatePipelineStatus(Job job, String action, String status);
}
