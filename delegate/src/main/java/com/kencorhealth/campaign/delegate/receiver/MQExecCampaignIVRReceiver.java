package com.kencorhealth.campaign.delegate.receiver;

import com.kencorhealth.campaign.dm.delegate.CampaignIVR;
import com.kencorhealth.campaign.mq.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
    
public class MQExecCampaignIVRReceiver extends MessageHandler<CampaignIVR> {
    private final static Logger log =
        LoggerFactory.getLogger(MQExecCampaignIVRReceiver.class);

    @Override
    public void process() throws Exception {
        CampaignIVR ci = data;
        
        log.debug("Received " + ci);
    }
}
