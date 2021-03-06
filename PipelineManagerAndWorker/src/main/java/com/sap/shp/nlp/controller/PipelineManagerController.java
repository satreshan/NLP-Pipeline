package com.sap.shp.nlp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sap.shp.nlp.model.Job;
import com.sap.shp.nlp.model.Message;
import com.sap.shp.nlp.model.Pipeline;
import com.sap.shp.nlp.model.PipelineAction;
import com.sap.shp.nlp.model.PipelineActions;
import com.sap.shp.nlp.model.PipelineConfiguration;
import com.sap.shp.nlp.model.Utility;
import com.sap.shp.nlp.repository.ManagerRepository;
import com.sap.shp.nlp.repository.PipelineActionRepository;
import com.sap.shp.nlp.service.PipelineService;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class PipelineManagerController {

	public static final Logger logger = LoggerFactory.getLogger(PipelineManagerController.class);
	
	@Autowired
	PipelineService service;

	@GetMapping(value = "/hello")
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok().body("Hello Shalini !");
	}


	/*
	 * Create pipeline
	 */
	@PostMapping(value = "/pipeline", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pipeline> createPipeline(@RequestBody PipelineConfiguration pipelineConfig){
		Pipeline saved = service.createPipeline(pipelineConfig);
		return ResponseEntity.ok().body(saved);
	}
	/*
	 * Get pipeline metadata by pipelineId
	 */
	@GetMapping(value = "/pipeline/metadata/id/{pipelineId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pipeline> getPipelineMetadataById(@PathVariable int pipelineId){
		return ResponseEntity.ok().body(service.getPipelineMetadataById(pipelineId));
	}
	
	/*
	 *  Get pipeline metadata by pipeline name
	 */
	@GetMapping(value = "/pipeline/metadata/name/{pipelineName}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Pipeline>> getPipelineMetadataByName(@PathVariable String pipelineName){
		return ResponseEntity.ok().body(service.getPipelineMetadataByName(pipelineName));
	}
	
	/*
	 * Get pipeline actions
	 */
	@GetMapping(value = "/pipeline/{pipelineId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PipelineActions>> getPipelineActions(@PathVariable int pipelineId){
		return ResponseEntity.ok().body(service.getPipelineActionsById(pipelineId));
	}
	
	/*
	 * Update pipeline
	 * Consider custom error messages.
	 */
	@PutMapping(value = "/pipeline/{pipelineId}",produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updatePipeline (@PathVariable int pipelineId, @RequestBody PipelineConfiguration pipelineConfig){
		Pipeline updatedPipeline = service.updatePipeline(pipelineConfig, pipelineId);
		if(updatedPipeline == null)
			return ResponseEntity.ok().body("Pipeline do not exist.");
		else
			return ResponseEntity.ok().body("Pipeline updated successfully.");
	}
	
	/*
	 * Delete pipeline
	 */
	@DeleteMapping(value = "/pipeline/{pipelineId}",produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deletePipeline(@PathVariable int pipelineId){
		boolean isDeleted = service.deletePipeline(pipelineId);
		if(isDeleted)
			return ResponseEntity.ok().body("Pipeline deleted successfully.");
		else
			return ResponseEntity.ok().body("Pipeline do not exist.");
	}
	
	/*
	 * Execute pipeline
	 */
	@PostMapping(value = "pipeline/execute/{pipelineId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Job> executePipeline(@PathVariable int pipelineId, @RequestBody Message message){
		Job job = service.executePipeline(pipelineId, message);
		if(job != null)
			return ResponseEntity.ok().body(job);
		else
			return ResponseEntity.ok().body(null);
	}
	
	/*
	 * Get job details
	 */
	@GetMapping(value = "pipeline/job/{jobId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Job> getJob(@PathVariable int jobId){
		return ResponseEntity.ok().body(service.getJobDetails(jobId));
	}
}
