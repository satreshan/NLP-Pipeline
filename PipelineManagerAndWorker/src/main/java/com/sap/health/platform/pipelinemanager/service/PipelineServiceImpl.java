package com.sap.health.platform.pipelinemanager.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sap.health.platform.pipelinemanager.exception.PipelineManagerException;
import com.sap.health.platform.pipelinemanager.kafka.Receiver;
import com.sap.health.platform.pipelinemanager.kafka.Sender;
import com.sap.health.platform.pipelinemanager.model.Job;
import com.sap.health.platform.pipelinemanager.model.Message;
import com.sap.health.platform.pipelinemanager.model.Pipeline;
import com.sap.health.platform.pipelinemanager.model.PipelineAction;
import com.sap.health.platform.pipelinemanager.model.PipelineActions;
import com.sap.health.platform.pipelinemanager.model.PipelineConfiguration;
import com.sap.health.platform.pipelinemanager.model.PipelineStatus;
import com.sap.health.platform.pipelinemanager.model.PipelineStatusId;
import com.sap.health.platform.pipelinemanager.model.Utility;
import com.sap.health.platform.pipelinemanager.repository.JobRepository;
import com.sap.health.platform.pipelinemanager.repository.ManagerRepository;
import com.sap.health.platform.pipelinemanager.repository.PipelineActionRepository;
import com.sap.health.platform.pipelinemanager.repository.PipelineStatusRepository;

@Service("service")
public class PipelineServiceImpl implements PipelineService {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	private Utility utility = new Utility();
	
	@Value("${kafka.topic.nlp}")
	private String topic;

	@Autowired
	private Sender sender;
	
	ManagerRepository managerRepository;
	PipelineActionRepository pipelineRepository;
	JobRepository jobRepository;
	PipelineStatusRepository statusRepository;
	
	@Inject
	public PipelineServiceImpl(ManagerRepository managerRepository, PipelineActionRepository pipelineRepository,
			JobRepository jobRepository,PipelineStatusRepository statusRepository) {
		this.managerRepository = managerRepository;
		this.pipelineRepository = pipelineRepository;
		this.jobRepository = jobRepository;
		this.statusRepository = statusRepository;
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
		
		/*
		 * Create the job
		 */
		Job job = new Job();
		job.setPipelineId(pipelineId);
		job.setDocumentId(message.getMetadata().getMessageId() + job.getJobId());
		job.setCreatedAt(utility.getTimestamp());
		Job saved = jobRepository.save(job);
		
		/*
		 * publish message on the nlp topic(considering static value for topic from
		 * properties)
		 */
		message.getMetadata().setJob(job);
		
		if (saved != null){
			/*
			 * Update status as pipeline initialized
			 */
			PipelineStatus status = initializePipeline(saved);
			if( status != null){
				statusRepository.save(status);
				if(sender.send(topic, message))				
					return saved;
				else{
					//Finalize pipeline with error
					status = finalizePipelineWithError(saved);
					statusRepository.save(status);
					return saved;
				}					
			}				
			else{
				return null;
			}			
		}
		else
			return null;
	}
	

	@Override
	public Job getJobDetails(int jobId) {
		// TODO Auto-generated method stub
		return jobRepository.findOne(jobId);
		
	}

	
	@Override
	public List<PipelineStatus> getPipelineStatus(int jobId) {
		// TODO Auto-generated method stub
		return statusRepository.findByJobId(jobId);
	}
	
	

	@Override
	public PipelineStatus getPipelineStatus(int jobId, String action) {
		// TODO Auto-generated method stub
		return statusRepository.findByJobIdAndAction(jobId, action);
	}
	
	@Override
	public PipelineActions getPipelineActionsByPipelineIdAndActionAndStatus(PipelineStatus status) {
		// TODO Auto-generated method stub
		return pipelineRepository.findByPipelineIdAndActionNameAndStatus(status.getPipelineId(), status.getAction(), status.getStatus());
	}
	
	

	@Override
	public boolean updatePipelineStatus(Job job, String action, String status) {
		// TODO Auto-generated method stub
		if(statusRepository.save(updateActionStaus(job, action, status)) != null){
			return true;
		}
		return false;
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

	private PipelineStatus initializePipeline(Job job){
		PipelineStatus status = new PipelineStatus();
		try {
			status.setJobId(job.getJobId());
			status.setDocumentId(job.getDocumentId());
			status.setPipelineId(job.getPipelineId());
			status.setAction("Initialize");
			status.setStatus("Initialized");
			status.setCreatedAt(job.getCreatedAt());
			return status;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private PipelineStatus finalizePipeline(Job job){
		PipelineStatus status = new PipelineStatus();
		try {
			status.setJobId(job.getJobId());
			status.setDocumentId(job.getDocumentId());
			status.setPipelineId(job.getPipelineId());
			status.setAction("Finalize");
			status.setStatus("Finalized");
			status.setCreatedAt(job.getCreatedAt());
			return status;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private PipelineStatus finalizePipelineWithError(Job job){
		PipelineStatus status = new PipelineStatus();
		try {
			status.setJobId(job.getJobId());
			status.setDocumentId(job.getDocumentId());
			status.setPipelineId(job.getPipelineId());
			status.setAction("Finalize");
			status.setStatus("FinalizedWithError");
			status.setCreatedAt(job.getCreatedAt());
			return status;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private PipelineStatus updateActionStaus(Job job, String action, String actionStatus){
		PipelineStatus status = new PipelineStatus();
		try {
			status.setJobId(job.getJobId());
			status.setDocumentId(job.getDocumentId());
			status.setPipelineId(job.getPipelineId());
			status.setAction(action);
			status.setStatus(actionStatus);
			status.setCreatedAt(job.getCreatedAt());
			return status;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
