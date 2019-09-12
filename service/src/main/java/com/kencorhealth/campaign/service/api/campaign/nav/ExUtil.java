package com.kencorhealth.campaign.service.api.campaign.nav;

import com.kencorhealth.campaign.ngin.ScriptUtil;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Executor;
import com.kencorhealth.campaign.dm.common.Script;
import com.kencorhealth.campaign.dm.delegate.CampaignDispatcher;
import com.kencorhealth.campaign.dm.delivery.nav.ProcessorBased;
import com.kencorhealth.campaign.dm.delivery.script.ScriptInput;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.mq.CMQFactory;

class ExUtil {
    static <T> Executor<T> getExecutor() {
        return new Executor<T>() {
            @Override
            public T execute(
                ScriptInput scriptInput,
                Object... arg) throws CampaignException {
                ProcessorBased pb =
                    CampaignUtil.extractFromData(ProcessorBased.class, arg);
                
                Script script = pb.getScript();
                
                return (T) ScriptUtil.invoke(script, scriptInput);
            }

            @Override
            public CampaignDispatcher getDispatcher() {
                return CMQFactory.getDispatcher();
            }
        };
    }
}
