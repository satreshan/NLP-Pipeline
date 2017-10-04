package com.sap.shp.nlp.repository;

import com.sap.shp.nlp.model.Pipeline;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("managerRepository")
public interface ManagerRepository extends CrudRepository<Pipeline, Integer>{
	List<Pipeline> findByPipelineName(String pipelineName);
}
