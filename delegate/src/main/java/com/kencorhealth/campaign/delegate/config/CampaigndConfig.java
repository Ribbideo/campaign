package com.kencorhealth.campaign.delegate.config;

import com.kencorhealth.campaign.mq.BrokerInfo;
import com.kencorhealth.campaign.twilio.TwilioConfig;
import com.kencorhealth.campaign.twilio.TwilioConfigProvider;
import io.dropwizard.Configuration;
import com.kencorhealth.campaign.mq.BrokerInfoProvider;

public class CampaigndConfig extends Configuration
    implements BrokerInfoProvider, TwilioConfigProvider {
    private TwilioConfig twilio;
    private MongoConfig mongo;
    private BrokerInfo broker;

    @Override
    public BrokerInfo getBroker() {
        return broker;
    }

    public void setBroker(BrokerInfo broker) {
        this.broker = broker;
    }
    
    @Override
    public TwilioConfig getTwilio() {
        return twilio;
    }

    public void setTwilio(TwilioConfig twilio) {
        this.twilio = twilio;
    }

    public MongoConfig getMongo() {
        return mongo;
    }

    public void setMongo(MongoConfig mongo) {
        this.mongo = mongo;
    }
}
