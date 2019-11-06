package com.kencorhealth.campaign.mq;

import com.kencorhealth.campaign.dm.config.BrokerConfig;

public interface BrokerConfigProvider {
    BrokerConfig getBroker();
}
