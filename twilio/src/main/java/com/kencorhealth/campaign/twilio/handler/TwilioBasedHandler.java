package com.kencorhealth.campaign.twilio.handler;

import com.kencorhealth.campaign.dm.common.AliasProvider;

public interface TwilioBasedHandler extends AliasProvider, AutoCloseable {
    @Override
    public default void close() {
        // Empty
    }
}
