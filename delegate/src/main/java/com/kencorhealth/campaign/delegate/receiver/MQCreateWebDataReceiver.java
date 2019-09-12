package com.kencorhealth.campaign.delegate.receiver;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.dm.input.WorkflowDataInput;
import com.kencorhealth.campaign.mq.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kencorhealth.campaign.db.handler.WorkflowDataHandler;

public class MQCreateWebDataReceiver extends MessageHandler<WorkflowDataInput> {
    private final static Logger log =
        LoggerFactory.getLogger(MQCreateWebDataReceiver.class);

    @Override
    public void process() throws Exception {
        WorkflowDataInput input = data;
        
        try (WorkflowDataHandler handler =
             CampaignFactory.get(WorkflowDataHandler.class)) {
            handler.add(input);
        }
    }
}
