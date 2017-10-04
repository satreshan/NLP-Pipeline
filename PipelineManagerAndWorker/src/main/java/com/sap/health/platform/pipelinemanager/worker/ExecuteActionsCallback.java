package com.sap.health.platform.pipelinemanager.worker;

import com.sap.health.platform.pipelinemanager.model.Message;

public interface ExecuteActionsCallback {

	boolean executeAction(Message message, String actionURL);
}
