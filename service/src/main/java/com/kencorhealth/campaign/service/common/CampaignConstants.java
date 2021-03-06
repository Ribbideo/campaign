package com.kencorhealth.campaign.service.common;

public interface CampaignConstants {
    String API = "api";
    String CAMPAIGN = "campaign";
    String AUTH = "auth";
    String CLINIC = "clinic";
    String MEMBER = "member";
    String EXECUTION = "execution";
    String SMS = "sms";
    String NAV = "nav";
    String IVR = "ivr";
    String RUNTIME = "runtime";
    String TOKEN = "token";
    String PARTICIPANT = "participant";
    String BY_NAME = "byName";
    String NAME = "name";
    String FIRST_NAME = "firstName";
    String LAST_NAME = "lastName";
    String TYPE = "type";

    String AUTHORIZATION = "authorization";

    String CLINIC_ID = "clinicId";
    String CAMPAIGN_ID = "campaignId";
    String FILE_ID = "fileId";
    String NAV_ID = "navId";
    String CONTAINER_ID = "containerId";
    String FILE = "file";
    String MEMBER_ID = "memberId";
    String PARTICIPANT_ID = "participantId";
    
    String CLINIC_ID_ENDPOINT = "{" + CLINIC_ID + "}";
    String MEMBER_ID_ENDPOINT = "{" + MEMBER_ID + "}";
    String CAMPAIGN_ID_ENDPOINT = "{" + CAMPAIGN_ID + "}";
    String FILE_ID_ENDPOINT = "{" + FILE_ID + "}";
    String NAV_ID_ENDPOINT = "{" + NAV_ID + "}";
    String PARTICIPANT_ID_ENDPOINT = "{" + PARTICIPANT_ID + "}";
    
    String NAME_ENDPOINT = "{" + NAME + "}";
    String FIRST_NAME_ENDPOINT = "{" + FIRST_NAME + "}";
    String LAST_NAME_ENDPOINT = "{" + LAST_NAME + "}";
    String TYPE_ENDPOINT = "{" + TYPE + "}";
}
