package com.kencorhealth.campaign.dm.common;

import com.kencorhealth.campaign.dm.delegate.CampaignDispatcher;
import com.kencorhealth.campaign.dm.delivery.script.ScriptInput;
import com.kencorhealth.campaign.dm.exception.CampaignException;

public interface Executor<T> {
    T execute(ScriptInput scriptInput, Object... args)
        throws CampaignException;
    CampaignDispatcher getDispatcher();
}
