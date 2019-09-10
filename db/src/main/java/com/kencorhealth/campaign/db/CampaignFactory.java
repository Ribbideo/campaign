package com.kencorhealth.campaign.db;

import com.kencorhealth.campaign.mongo.MongoFactory;
import com.kencorhealth.campaign.mongo.handler.MongoHandler;

public class CampaignFactory {
    public static void init(String mongoUri) {
        MongoFactory.init(mongoUri);
    }
    
    public static <MH extends MongoHandler> MH get(Class<MH> handlerClass) {
        return MongoFactory.get(handlerClass);
    }
}
