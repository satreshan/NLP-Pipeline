package com.sap.shp.nlp.worker;

import com.sap.shp.nlp.model.Message;

public interface ExecuteActionsCallback {

	boolean executeAction(Message message);
}
