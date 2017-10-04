package com.sap.health.platform.pipelinemanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sap.health.platform.pipelinemanager.model.Job;

@Repository("jobRepository")
public interface JobRepository extends CrudRepository<Job, Integer> {

}
