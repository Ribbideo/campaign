package com.kencorhealth.campaign.delegate.handler;

import com.kencorhealth.campaign.dm.delegate.CampaignForm;
import com.kencorhealth.campaign.dm.delivery.nav.ProcessorBased;
import com.kencorhealth.campaign.mq.MessageHandler;
import com.kencorhealth.campaign.ngin.ScriptUtil;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQSubmitCampaignFormHandler extends MessageHandler<CampaignForm> {
    private final static Logger log =
        LoggerFactory.getLogger(MQSubmitCampaignFormHandler.class);

    @Override
    public void process() throws Exception {
        CampaignForm cf = data;
        
        String campaignId = cf.getCampaignId();
        
        Map<String, Object> campaign = new HashMap();
        campaign.put("campaignId", campaignId);
        campaign.put("providerId", cf.getProviderId());
        
        Map<String, Object> input = new HashMap();
        input.put("campaign.ui", cf.getData());
        input.put("campaign.campaign", campaign);
        
        ProcessorBased pb = cf.getProcessor();
        ScriptUtil.invoke(pb.getScript(), input);
    }
}
