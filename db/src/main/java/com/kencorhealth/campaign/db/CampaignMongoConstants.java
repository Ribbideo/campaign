package com.kencorhealth.campaign.db;

import com.kencorhealth.campaign.mongo.MongoConstants;

public interface CampaignMongoConstants extends MongoConstants {
    // Collections
    String MEMBER_COLLECTION = "Member";
    String CAMPAIGN_COLLECTION = "Campaign";
    String WORKFLOW_DATA_COLLECTION = "WorkflowData";
    String AUTH_TOKEN_COLLECTION = "AuthToken";
    String CAMPAIGN_DATABASE = "campaigndb";
    String PARTICIPANT_COLLECTION = "Participant";

    // JWT-related
    String JWT_ISSUER = "KencorHealth";
    String JWT_SUBJECT = "AuthToken";
        
    // Keys
    String OR_KEY = "$or";
    String AND_KEY = "$and";
    String CLINIC_TYPE_KEY = "clinicType";
    String CLINIC_ID_KEY = "clinicId";
    String USER_ID_KEY = "userId";
    String IN_USE_KEY = "inUse";
    String CAMPAIGN_ID_KEY = "campaignId";
    String LAST_NAME_KEY = "lastName";
    String FIRST_NAME_KEY = "firstName";
    String MOBILE_NUMBER_KEY = "mobileNumber";
    String PHONE_NUMBER_KEY = "phoneNumber";
    String EXTRA_KEY = "extra";
    String ROLE_INFO_KEY = "roleInfo";
    String JWT_KEY = "jwt";
    String ROLE_KEY = "role";
    String FILE_ID_KEY = "fileId";
    String EMAIL_KEY = "email";
}
