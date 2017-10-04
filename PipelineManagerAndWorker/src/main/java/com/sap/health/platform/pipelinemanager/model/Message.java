package com.sap.health.platform.pipelinemanager.model;

import java.io.Serializable;

public class Message{
	/*
	 * Message format need to be clarified.
	 * 
	 */	
	
	private MessageMetadata metadata;
	private Object content;

	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(MessageMetadata metadata) {
		this.metadata = metadata;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}


}
