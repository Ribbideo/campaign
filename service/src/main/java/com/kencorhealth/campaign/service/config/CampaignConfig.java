package com.kencorhealth.campaign.service.config;

import com.kencorhealth.campaign.cdn.S3Config;
import com.kencorhealth.campaign.mq.BrokerInfo;
import io.dropwizard.Configuration;
import com.kencorhealth.campaign.mq.BrokerInfoProvider;

public class CampaignConfig extends Configuration
    implements BrokerInfoProvider {
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
    
    public MongoConfig getMongo() {
        return mongo;
    }

    public void setMongo(MongoConfig mongo) {
        this.mongo = mongo;
    }
}
