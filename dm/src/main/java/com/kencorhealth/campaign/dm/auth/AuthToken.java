package com.kencorhealth.campaign.dm.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kencorhealth.campaign.dm.common.Identified;
import com.kencorhealth.campaign.dm.provider.Role;
import java.security.Principal;

public class AuthToken extends Identified implements Principal {
    private String jwt;
    private String userId;
    private String providerId;
    private Role role;

    @JsonIgnore
    @Override
    public String getName() {
        return userId;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
    
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return
            "AuthToken{" + "userId=" + userId + ", role=" +
            role + ", providerId=" + providerId + ", jwt=" + jwt +
            "}, " + super.toString();
    }
}
