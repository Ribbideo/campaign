package com.kencorhealth.campaign.service.api.campaign.nav;

import com.kencorhealth.campaign.ngin.ScriptUtil;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Executor;
import com.kencorhealth.campaign.dm.common.Script;
import com.kencorhealth.campaign.dm.delegate.CampaignForm;
import com.kencorhealth.campaign.dm.delivery.nav.Evaluator;
import com.kencorhealth.campaign.dm.delivery.nav.PreProcessor;
import com.kencorhealth.campaign.dm.delivery.nav.Processor;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.mq.CMQFactory;
import java.io.IOException;
import java.util.Map;

class ExUtil {
    static Executor<Boolean> getExecutor(String campaignId, String formId) {
        return new Executor<Boolean>() {
            @Override
            public Boolean execute(Map<String, Object> data, Object... arg)
                throws CampaignException {
                boolean retVal = false;
                
                PreProcessor preProcessor =
                    CampaignUtil.extractFromData(PreProcessor.class, arg);
                
                if (preProcessor != null) {
                    Script script = preProcessor.getScript();

                    retVal = (boolean) ScriptUtil.invoke(script, data);
                } else {
                    retVal = true;
                }
                
                if (retVal) {
                    Processor processor =
                        CampaignUtil.extractFromData(Processor.class, arg);

                    if (processor != null) {
                        CampaignForm cf = new CampaignForm();
                        cf.setCampaignId(campaignId);
                        cf.setProcessor(processor);
                        cf.setFormId(formId);
                        cf.setData(data);

                        try {
                            CMQFactory
                                .getDispatcher()
                                .dispatchSubmitCampaignForm(cf);
                        } catch (IOException e) {
                            throw new CampaignException(e);
                        }
                        
                        retVal = true;
                    }
                }
                
                return retVal;
            }
        };
    }

    static Evaluator getEvaluator() {
        return new Evaluator() {
            @Override
            public boolean evaluate(Map<String, Object> data, String expression)
                throws CampaignException {
                boolean retVal = false;
                
                String[] split = expression.split(" ");
                
                String key = split[0];
                String comparison = split[1];
                String value = split[2].replaceAll("\"", "");
                
                switch (comparison) {
                    case "==":
                        if (data.containsKey(key)) {
                            retVal = data.get(key).equals(value);
                        }
                        break;
                    case "!=":
                        if (data.containsKey(key)) {
                            retVal = !data.get(key).equals(value);
                        } else {
                            retVal = true;
                        }
                        break;
                }
                
                return retVal;
            }
        };
    }
}
