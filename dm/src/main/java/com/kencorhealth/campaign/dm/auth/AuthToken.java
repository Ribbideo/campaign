package com.kencorhealth.campaign.dm.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kencorhealth.campaign.dm.common.AliasProvider;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Identified;
import com.kencorhealth.campaign.dm.rpm.RpmInfo;
import java.security.Principal;

public class AuthToken extends Identified implements AliasProvider, Principal {
    private String jwt;
    private RpmInfo rpm;
    private String userId;

    @Override
    public String alias() {
        return "auth";
    }
    
    @JsonIgnore
    @Override
    public String getName() {
        return userId;
    }

    public String clinicId() {
        return rpm.getClinic().getId();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RpmInfo getRpm() {
        return rpm;
    }

    public void setRpm(RpmInfo rpm) {
        this.rpm = rpm;
        userId = rpm.userId();
    }
    
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return
            "AuthToken{" + "jwt=" + CampaignUtil.protect(jwt) +
            ", rpm=" + rpm + ", userId=" + userId + '}';
    }
}
