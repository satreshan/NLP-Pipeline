package com.sap.shp.nlp.model;

import java.io.Serializable;

public class Message{
	/*
	 * Message format need to be clarified.
	 * 
	 */
	
	private String documentId;
	private String document;
	private String documentType;
	private int pipelineId;

	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Message(String documentId, String document, String documentType) {
		super();
		this.documentId = documentId;
		this.document = document;
		this.documentType = documentType;
		this.pipelineId = 0;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	
	public int getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(int pipelineId) {
		this.pipelineId = pipelineId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Message [ Id = " + documentId + " Type = " + documentType + " Document = " + document;
	}

}
