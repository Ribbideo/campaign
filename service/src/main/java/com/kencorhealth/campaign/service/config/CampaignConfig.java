package com.kencorhealth.campaign.service.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kencorhealth.campaign.cdn.S3Config;
import com.kencorhealth.campaign.dm.config.MongoConfig;
import com.kencorhealth.campaign.dm.config.BrokerConfig;
import com.kencorhealth.campaign.dm.config.RouterConfig;
import io.dropwizard.Configuration;
import com.palantir.websecurity.WebSecurityConfigurable;
import com.palantir.websecurity.WebSecurityConfiguration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.kencorhealth.campaign.mq.BrokerConfigProvider;

public class CampaignConfig extends Configuration
    implements BrokerConfigProvider, WebSecurityConfigurable {
    private MongoConfig mongo;
    private BrokerConfig broker;
    private RouterConfig router;
    private S3Config s3;

    @JsonProperty("webSecurity")
    @NotNull
    @Valid
    private final WebSecurityConfiguration webSecurity = WebSecurityConfiguration.DEFAULT;

    @Override
    public WebSecurityConfiguration getWebSecurityConfiguration() {
        return this.webSecurity;
    }

    public RouterConfig getRouter() {
        return router;
    }

    public void setRouter(RouterConfig router) {
        this.router = router;
    }

    public S3Config getS3() {
        return s3;
    }

    public void setS3(S3Config s3) {
        this.s3 = s3;
    }
    
    @Override
    public BrokerConfig getBroker() {
        return broker;
    }

    public void setBroker(BrokerConfig broker) {
        this.broker = broker;
    }
    
    public MongoConfig getMongo() {
        return mongo;
    }

    public void setMongo(MongoConfig mongo) {
        this.mongo = mongo;
    }
}
