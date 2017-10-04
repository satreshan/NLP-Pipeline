package com.sap.shp.nlp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sap.shp.nlp.model.Job;

@Repository("jobRepository")
public interface JobRepository extends CrudRepository<Job, Integer> {

}
