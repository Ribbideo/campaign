package com.kencorhealth.campaign.dm.common;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.Map;

public interface ScriptExecutor {
    void execute(Script script, Map<String, Object> data)
        throws CampaignException;
}
