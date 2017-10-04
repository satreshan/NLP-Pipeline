package com.sap.health.platform.pipelinemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sap.health.platform.pipelinemanager.model.PipelineStatus;

@Repository("statusRepository")
public interface PipelineStatusRepository extends CrudRepository<PipelineStatus, Integer> {
	List<PipelineStatus> findByJobId(int jobId);
	
	@Query("select p from PipelineStatus p where p.jobId = ?1 and p.action = ?2")
	PipelineStatus findByJobIdAndAction(int JobId, String Action);
}
