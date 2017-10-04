package com.sap.shp.nlp.service;

import java.sql.Timestamp;
//import com.sap.shp.nlp.kafka.producer.Sender;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sap.shp.nlp.kafka.Receiver;
import com.sap.shp.nlp.kafka.Sender;
//import com.sap.shp.nlp.kafka.producer.Sender;
import com.sap.shp.nlp.model.Job;
import com.sap.shp.nlp.model.Message;
import com.sap.shp.nlp.model.Pipeline;
import com.sap.shp.nlp.model.PipelineAction;
import com.sap.shp.nlp.model.PipelineActions;
import com.sap.shp.nlp.model.PipelineConfiguration;
import com.sap.shp.nlp.model.Utility;
import com.sap.shp.nlp.repository.JobRepository;
import com.sap.shp.nlp.repository.ManagerRepository;
import com.sap.shp.nlp.repository.MessageRepository;
import com.sap.shp.nlp.repository.PipelineActionRepository;

//import kafkaapi.Producer;

@Service("service")
public class PipelineServiceImpl implements PipelineService {

	private Utility utility = new Utility();
	@Value("${kafka.topic.nlp}")
	private String topic;

	@Autowired
	private Sender sender;
	
	ManagerRepository managerRepository;
	PipelineActionRepository pipelineRepository;
	JobRepository jobRepository;
	
	@Inject
	public PipelineServiceImpl(ManagerRepository managerRepository, PipelineActionRepository pipelineRepository,
			JobRepository jobRepository) {
		this.managerRepository = managerRepository;
		this.pipelineRepository = pipelineRepository;
		this.jobRepository = jobRepository;
	}

	@Override
	public Pipeline createPipeline(PipelineConfiguration pipelineConfig) {
		// TODO Auto-generated method stub
		Date date = new Date();
		Pipeline pipeline = pipelineConfig.getMetadata();
		pipeline.setCreatedAt(new Timestamp(date.getTime()));
		Pipeline saved = managerRepository.save(pipeline);
		// Call to register pipelineActions
		List<PipelineActions> pipelineactions = createActionStateMapping(pipelineConfig.getActions(),
				saved.getPipelineId());
		pipelineRepository.save(pipelineactions);
		return saved;
	}

	@Override
	public Pipeline updatePipeline(PipelineConfiguration pipelineConfig, int id) {
		// TODO Auto-generated method stub

		Pipeline fetched = managerRepository.findOne(id);
		if (fetched == null)
			return null;
		else {
			Date date = new Date();
			Pipeline pipeline = pipelineConfig.getMetadata();
			fetched.setCreatedAt(new Timestamp(date.getTime()));
			fetched.setPipelineName(pipeline.getPipelineName());
			fetched.setDescription(pipeline.getDescription());
			Pipeline saved = managerRepository.save(fetched);
			// Call to register pipelineActions
			List<PipelineActions> pipelineactions = createActionStateMapping(pipelineConfig.getActions(),
					saved.getPipelineId());
			// System.out.println("No of rows deleted:
			// ***************"+deletePipelineActions(id));
			pipelineRepository.save(pipelineactions);
			return saved;
		}

	}

	@Override
	public boolean deletePipeline(int id) {
		// TODO Auto-generated method stub
		Pipeline fetched = managerRepository.findOne(id);
		List<PipelineActions> pipelineactions = (List<PipelineActions>) pipelineRepository.findAll();
		if (fetched == null)
			return false;
		else {
			managerRepository.delete(id);
			pipelineRepository.delete(pipelineactions);
			return true;
		}

	}

	@Override
	public List<PipelineActions> getPipelineActionsById(int id) {
		// TODO Auto-generated method stub
		List<PipelineActions> actions = (List<PipelineActions>) pipelineRepository.findPipelineActionsByPipelineId(id);
		return actions;
	}

	@Override
	public Pipeline getPipelineMetadataById(int id) {
		// TODO Auto-generated method stub
		Pipeline pipeline = managerRepository.findOne(id);
		return pipeline;
	}

	@Override
	public List<Pipeline> getPipelineMetadataByName(String pipelineName) {
		// TODO Auto-generated method stub
		return managerRepository.findByPipelineName(pipelineName);
	}

	@Override
	public Job executePipeline(int pipelineId, Message message) {
		// TODO Auto-generated method stub
		message.setPipelineId(pipelineId);
		/*
		 * publish message on the nlp topic(considering static value from
		 * properties)
		 */
		sender.send(topic, message);
		Job job = new Job();
		job.setPipelineId(pipelineId);
		job.setCreatedAt(utility.getTimestamp());
		Job saved = jobRepository.save(job);
		if (saved != null)
			return job;
		else
			return null;
	}

	/*
	 * Private methods
	 */
	private List<PipelineActions> createActionStateMapping(List<PipelineAction> actions, int id) {
		List<PipelineActions> pipelineActions = new ArrayList<PipelineActions>();
		pipelineActions.add(utility.getInitialPipelineAction(id));
		for (int i = 0; i < actions.size(); i++) {
			pipelineActions.get(i).setNextAction(actions.get(i).getActionName());
			pipelineActions.add(utility.getPipelineAction(false, actions.get(i), id));
		}
		for (int i = 0; i < actions.size(); i++) {
			pipelineActions.add(utility.getPipelineAction(true, actions.get(i), id));
		}
		pipelineActions.add(utility.getFinalPipelineAction(false, id));
		pipelineActions.add(utility.getFinalPipelineAction(true, id));
		return pipelineActions;
	}

	@Override
	public Job getJobDetails(int jobId) {
		// TODO Auto-generated method stub
		return jobRepository.findOne(jobId);
		
	}
}
