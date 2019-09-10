package com.kencorhealth.campaign.dm.common;

public enum Gender {
    MALE,
    FEMALE,
    NOT_SPECIFIED;
    
    public static Gender from(com.ribbideo.dm.shared.Gender gender) {
        Gender retVal = null;
    
        if (gender != null) {
            switch (gender) {
                case FEMALE:
                    retVal = FEMALE;
                    break;
                case MALE:
                    retVal = MALE;
                    break;
                case NOT_SPECIFIED:
                    retVal = NOT_SPECIFIED;
                    break;
            }
        }
        
        return retVal;
    }
}
