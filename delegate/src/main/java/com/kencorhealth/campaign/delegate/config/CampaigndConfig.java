package com.kencorhealth.campaign.delegate.config;

import com.kencorhealth.campaign.cdn.S3Config;
import com.kencorhealth.campaign.dm.config.BrokerConfig;
import com.kencorhealth.campaign.dm.config.MongoConfig;
import com.kencorhealth.campaign.dm.config.RouterConfig;
import com.kencorhealth.campaign.twilio.TwilioConfig;
import com.kencorhealth.campaign.twilio.TwilioConfigProvider;
import io.dropwizard.Configuration;
import com.kencorhealth.campaign.mq.BrokerConfigProvider;

public class CampaigndConfig extends Configuration
    implements BrokerConfigProvider, TwilioConfigProvider {
    private TwilioConfig twilio;
    private MongoConfig mongo;
    private BrokerConfig broker;
    private RouterConfig router;
    private S3Config s3;

    public S3Config getS3() {
        return s3;
    }

    public void setS3(S3Config s3) {
        this.s3 = s3;
    }

    public RouterConfig getRouter() {
        return router;
    }

    public void setRouter(RouterConfig router) {
        this.router = router;
    }
    
    @Override
    public BrokerConfig getBroker() {
        return broker;
    }

    public void setBroker(BrokerConfig broker) {
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
