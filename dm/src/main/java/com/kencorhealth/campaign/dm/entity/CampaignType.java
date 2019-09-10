package com.kencorhealth.campaign.dm.entity;

import com.ribbideo.dm.input.Stringifier;

public enum CampaignType implements Stringifier {
    PATIENT_ENROLMENT,
    HEALTH_EVENT_ENROLMENT_ENGAGEMENT,
    USER_NON_COMPLIANCE_ENGAGEMENT,
    PATIENT_SATISFACTION_SURVEY,
    CUSTOM;

    @Override
    public String stringify() {
        String retVal = null;
        
        switch (this) {
            case CUSTOM:
                retVal = "Custom";
                break;
            case HEALTH_EVENT_ENROLMENT_ENGAGEMENT:
                retVal = "Health Event Enrolment Engagement";
                break;
            case PATIENT_ENROLMENT:
                retVal = "Patient Enrolment";
                break;
            case PATIENT_SATISFACTION_SURVEY:
                retVal = "Patient Satisfaction Survey";
                break;
            case USER_NON_COMPLIANCE_ENGAGEMENT:
                retVal = "User non-compliance engagement";
                break;
        }
        
        return retVal;
    }
}
