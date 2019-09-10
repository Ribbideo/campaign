package com.kencorhealth.campaign.dm.delivery.nav;

import com.kencorhealth.campaign.dm.common.Executor;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.Map;

public interface Executable {
    Nav execute(Map<String, Object> data, Executor executor)
        throws CampaignException;
}
