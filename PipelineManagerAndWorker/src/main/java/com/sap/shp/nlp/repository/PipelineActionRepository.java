package com.sap.shp.nlp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sap.shp.nlp.model.PipelineActions;
import com.sap.shp.nlp.model.PipelineActionsId;

@Repository("pipelineRepository")
public interface PipelineActionRepository extends CrudRepository<PipelineActions, PipelineActionsId> {
	List<PipelineActions> findPipelineActionsByPipelineId(int pipelineId);
	
	@Modifying
	@Query("delete from PipelineActions p where p.pipelineId = ?1")
	int deleteByPipelineId(int pipelineId);
}
