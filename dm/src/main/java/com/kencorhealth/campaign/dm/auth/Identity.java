package com.kencorhealth.campaign.dm.auth;

import com.kencorhealth.campaign.dm.common.CampaignUtil;

public class Identity {
    private IdentityType type;
    private String value;

    public IdentityType getType() {
        return type;
    }

    public void setType(IdentityType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return
            "Identity{" + "type=" + type + ", value=" +
            CampaignUtil.protect(value) + '}';
    }
}
