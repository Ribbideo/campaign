package com.kencorhealth.campaign.dm.delivery.nav;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.Map;

public interface Evaluatable {
    Nav evaluate(Map<String, Object> data, Evaluator evaluator)
        throws CampaignException;
}
