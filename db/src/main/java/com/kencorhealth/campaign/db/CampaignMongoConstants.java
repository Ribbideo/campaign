package com.kencorhealth.campaign.db;

import com.kencorhealth.campaign.mongo.MongoConstants;

public interface CampaignMongoConstants extends MongoConstants {
    // Collections
    String PROVIDER_COLLECTION = "Provider";
    String MEMBER_COLLECTION = "Member";
    String CAMPAIGN_COLLECTION = "Campaign";
    String PARTICIPANT_TOKEN_COLLECTION = "ParticipantToken";
    String CAMPAIGN_DATABASE = "campaigndb";
    String PARTICIPANT_COLLECTION = "Participant";
    
    // Keys
    String PROVIDER_TYPE_KEY = "providerType";
    String PROVIDER_ID_KEY = "providerId";
    String CAMPAIGN_ID_KEY = "campaignId";
    String LAST_NAME_KEY = "lastName";
    String FIRST_NAME_KEY = "firstName";
    String MOBILE_NUMBER_KEY = "mobileNumber";
}
