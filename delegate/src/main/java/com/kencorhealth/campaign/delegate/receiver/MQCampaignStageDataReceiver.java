package com.kencorhealth.campaign.delegate.receiver;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.dm.delegate.CampaignStageData;
import com.kencorhealth.campaign.dm.delivery.nav.ProcessorBased;
import com.kencorhealth.campaign.mq.MessageHandler;
import com.kencorhealth.campaign.ngin.ScriptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kencorhealth.campaign.db.handler.WorkflowDataHandler;
import com.kencorhealth.campaign.dm.delivery.nav.Nav;
import com.kencorhealth.campaign.dm.delivery.script.ScriptContext;
import com.kencorhealth.campaign.dm.delivery.script.ScriptInput;
import java.util.Map;

public class MQCampaignStageDataReceiver
    extends MessageHandler<CampaignStageData> {
    private final static Logger log =
        LoggerFactory.getLogger(MQCampaignStageDataReceiver.class);

    @Override
    public void process() throws Exception {
        CampaignStageData csd = data;
        
        ScriptInput si = data.getScriptInput();
        ScriptContext cd = si.getContext();
        Nav nav = si.getNav();
        
        String campaignId = cd.getCampaignId();
        String containerId = cd.getContainerId();
        String providerId = cd.getAuthToken().getProviderId();
        
        Map<String, Object> formData = si.getFormData();
        
        if (formData != null) {
            try (WorkflowDataHandler wdh =
                 CampaignFactory.get(WorkflowDataHandler.class)) {

                wdh.update(
                    providerId,
                    campaignId,
                    containerId,
                    nav.getId(),
                    si.getFormData()
                );
            }
        }
        
        ProcessorBased pb = csd.getProcessor();
        
        if (pb != null) {
            ScriptUtil.invoke(pb.getScript(), si);
        }
    }
}
