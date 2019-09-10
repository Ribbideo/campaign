package com.kencorhealth.campaign.mq;

public class CMQFactory {
    public static void init(BrokerInfoProvider bcp) {
        MQFactory.init(bcp.getBroker());
    }

    public static CampaignDispatcher getDispatcher() {
        return new CampaignDispatcherImpl();
    }
}
