package com.kencorhealth.campaign.dm.input;

import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.auth.Identity;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Input;
import com.kencorhealth.campaign.dm.clinic.Role;

public class AuthInput extends Input<AuthToken> {
    private Identity identity;
    private String password;
    private Role role;
    
    @Override
    public AuthToken convert() {
        AuthToken retVal = new AuthToken();

        retVal.autoFill();
        
        return retVal;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return
            "AuthInput{" + "identity=" + identity + ", password=" +
            CampaignUtil.protect(password) + ", role=" + role + '}';
    }
}
