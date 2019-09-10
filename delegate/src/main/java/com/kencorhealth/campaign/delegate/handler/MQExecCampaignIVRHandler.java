package com.kencorhealth.campaign.delegate.handler;

import com.kencorhealth.campaign.dm.delegate.CampaignIVR;
import com.kencorhealth.campaign.mq.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
    
public class MQExecCampaignIVRHandler extends MessageHandler<CampaignIVR> {
    private final static Logger log =
        LoggerFactory.getLogger(MQExecCampaignIVRHandler.class);

    @Override
    public void process() throws Exception {
        CampaignIVR ci = data;
        
        log.debug("Received " + ci);
    }
}
