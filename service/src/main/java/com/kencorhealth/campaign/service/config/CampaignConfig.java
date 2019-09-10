package com.kencorhealth.campaign.service.config;

import com.kencorhealth.campaign.mq.BrokerInfo;
import io.dropwizard.Configuration;
import com.kencorhealth.campaign.mq.BrokerInfoProvider;

public class CampaignConfig extends Configuration
    implements BrokerInfoProvider {
    private MongoConfig mongo;
    private BrokerInfo broker;

    @Override
    public BrokerInfo getBroker() {
        return broker;
    }

    public void setBroker(BrokerInfo broker) {
        this.broker = broker;
    }
    
    public MongoConfig getMongo() {
        return mongo;
    }

    public void setMongo(MongoConfig mongo) {
        this.mongo = mongo;
    }
}
