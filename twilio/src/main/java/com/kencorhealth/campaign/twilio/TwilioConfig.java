package com.kencorhealth.campaign.twilio;

public class TwilioConfig implements TwilioConfigProvider {
    private String adminNumber;
    private String accountSid;
    private String authToken;

    public String getAdminNumber() {
        return adminNumber;
    }

    public void setAdminNumber(String adminNumber) {
        this.adminNumber = adminNumber;
    }
    
    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public TwilioConfig getTwilio() {
        return this;
    }
}
