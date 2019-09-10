package com.kencorhealth.campaign.dm.delivery.nav;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.Map;

public interface Evaluator {
    boolean evaluate(Map<String, Object> data, String expression)
        throws CampaignException;
}
