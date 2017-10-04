package com.sap.health.platform.pipelinemanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sap.health.platform.pipelinemanager.model.KafkaMessage;

@Repository("messageRepository")
public interface MessageRepository extends CrudRepository<KafkaMessage, Integer> {
	
}
