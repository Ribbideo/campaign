package com.kencorhealth.campaign.http.base.handler;

public interface HttpBasedHandler extends AutoCloseable {
    void setBaseUrl(String baseUrl);
    void setAuthorization(String authorization);
    @Override
    public default void close() {
        // Empty for now
    }
}
