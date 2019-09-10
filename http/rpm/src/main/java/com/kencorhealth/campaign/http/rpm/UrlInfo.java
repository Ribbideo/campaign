package com.kencorhealth.campaign.http.rpm;

public class UrlInfo {
    private String baseUrl;
    private String userName;
    private String password;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
            "UrlInfo{" + "baseUrl=" + baseUrl + ", userName=" + userName +
            ", password=" + password + '}';
    }
}
