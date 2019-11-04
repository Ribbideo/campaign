package com.kencorhealth.campaign.dm.common;

import com.ribbideo.dm.input.Stringifier;

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
