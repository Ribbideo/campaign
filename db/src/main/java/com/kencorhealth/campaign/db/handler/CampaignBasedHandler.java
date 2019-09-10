package com.kencorhealth.campaign.db.handler;

import com.kencorhealth.campaign.mongo.handler.MongoHandler;
import com.kencorhealth.campaign.dm.common.Identified;
import com.kencorhealth.campaign.dm.common.Input;
import com.kencorhealth.campaign.db.CampaignMongoConstants;

public interface CampaignBasedHandler<T extends Identified, TI extends Input>
    extends MongoHandler<T, TI>, CampaignMongoConstants {
    @Override
    default String databaseName() {
        return CAMPAIGN_DATABASE;
    }
}
