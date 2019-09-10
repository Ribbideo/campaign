package com.kencorhealth.campaign.mongo;

import com.codahale.metrics.health.HealthCheck;
import com.kencorhealth.campaign.mongo.handler.MongoHandler;

public class MongoHealthCheck<MH extends MongoHandler> extends HealthCheck {
    private final Class<MH> handlerType;
    
    public MongoHealthCheck(Class<MH> handlerType) {
        this.handlerType = handlerType;
    }
    
    @Override
    protected Result check() throws Exception {
        Result retVal = null;
        
        try (MH handler = MongoFactory.get(handlerType)) {
            handler.ping();
            retVal = Result.healthy();
        } catch (Exception e) {
            retVal = Result.unhealthy("MongoDB unhealthy", e);
        }
        
        return retVal;
    }
}