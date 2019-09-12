package com.kencorhealth.campaign.dm.delivery.nav;

import com.kencorhealth.campaign.dm.common.Executor;
import com.kencorhealth.campaign.dm.delivery.script.ScriptInput;
import com.kencorhealth.campaign.dm.exception.CampaignException;

public interface Executable {
    Nav execute(ScriptInput scriptInput, Executor executor)
        throws CampaignException;
}
