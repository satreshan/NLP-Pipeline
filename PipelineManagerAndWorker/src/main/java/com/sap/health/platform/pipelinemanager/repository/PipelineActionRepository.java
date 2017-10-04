package com.sap.health.platform.pipelinemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sap.health.platform.pipelinemanager.model.PipelineActions;
import com.sap.health.platform.pipelinemanager.model.PipelineActionsId;

@Repository("pipelineRepository")
public interface PipelineActionRepository extends CrudRepository<PipelineActions, PipelineActionsId> {
	List<PipelineActions> findPipelineActionsByPipelineId(int pipelineId);
	PipelineActions findByPipelineIdAndActionNameAndStatus(int pipelineId, String actionName, String status);
	@Modifying
	@Query("delete from PipelineActions p where p.pipelineId = ?1")
	int deleteByPipelineId(int pipelineId);
}
