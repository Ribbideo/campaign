package com.kencorhealth.campaign.mq;

import com.kencorhealth.campaign.dm.delegate.CampaignDispatcher;

public class CMQFactory {
    public static void init(BrokerInfoProvider bcp) {
        MQFactory.init(bcp.getBroker());
    }

    public static CampaignDispatcher getDispatcher() {
        return new CampaignDispatcherImpl();
    }
}
