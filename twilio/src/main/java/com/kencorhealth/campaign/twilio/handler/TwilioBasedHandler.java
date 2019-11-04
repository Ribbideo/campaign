package com.kencorhealth.campaign.twilio.handler;

public interface TwilioBasedHandler extends AutoCloseable {
    String alias();

    @Override
    public default void close() {
        // Empty
    }
}
