package com.kencorhealth.campaign.dm.common;

public enum PhoneType implements Stringifier {
   MOBILE,
   LANDLINE;
    
    public static PhoneType from(String type) {
        return
            type.equals("mobile") ?
            MOBILE :
            type.equals("landline") ?
            LANDLINE : null;
    }

    @Override
    public String stringify() {
        return name().toLowerCase();
    }
}
