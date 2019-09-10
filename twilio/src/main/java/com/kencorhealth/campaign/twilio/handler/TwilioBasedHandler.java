package com.kencorhealth.campaign.twilio.handler;

public interface TwilioBasedHandler extends AutoCloseable {
    @Override
    public default void close() {
        // Empty
    }
}
