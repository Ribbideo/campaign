package com.kencorhealth.campaign.dm.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kencorhealth.campaign.dm.common.Identified;
import com.kencorhealth.campaign.dm.provider.Role;
import java.security.Principal;

public class AuthToken extends Identified implements Principal {
    private String jwt;
    private String userId;
    private String providerId;
    private String approverId;
    private String approverName;
    private Role role;

    @JsonIgnore
    @Override
    public String getName() {
        return userId;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
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
            ", approverId=" + approverId + ", approverName=" +
            approverName + "}, " + super.toString();
    }
}
