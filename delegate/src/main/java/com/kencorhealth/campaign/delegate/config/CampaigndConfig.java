package com.kencorhealth.campaign.delegate.config;

import com.kencorhealth.campaign.cdn.S3Config;
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
    private S3Config s3;

    public S3Config getS3() {
        return s3;
    }

    public void setS3(S3Config s3) {
        this.s3 = s3;
    }

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
