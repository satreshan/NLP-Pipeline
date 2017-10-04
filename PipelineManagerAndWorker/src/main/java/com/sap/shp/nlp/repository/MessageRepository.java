package com.sap.shp.nlp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sap.shp.nlp.model.KafkaMessage;

@Repository
public interface MessageRepository extends CrudRepository<KafkaMessage, Integer> {

}
