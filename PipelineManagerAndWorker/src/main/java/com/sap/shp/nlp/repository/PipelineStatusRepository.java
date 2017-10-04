package com.sap.shp.nlp.repository;

import org.springframework.data.repository.CrudRepository;
import com.sap.shp.nlp.model.PipelineStatus;

public interface PipelineStatusRepository extends CrudRepository<PipelineStatus, Long> {

}
