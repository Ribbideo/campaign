package com.kencorhealth.campaign.mq;

import com.kencorhealth.campaign.dm.config.BrokerConfig;

public class MQFactory implements AMQPProvider, CMQConstants {
    private static MQFactory instance;

    private final BrokerConfig brokerInfo;

    private MQFactory(BrokerConfig brokerInfo) {
        this.brokerInfo = brokerInfo;
    }

    public static MQFactory init(BrokerConfig brokerInfo) {
        if (instance == null) {
            instance = new MQFactory(brokerInfo);
        }

        return instance;
    }

    public static MQFactory get() {
        return instance;
    }

    @Override
    public String getAMQPHostname() {
        return brokerInfo.getHost();
    }

    @Override
    public int getAMQPPort() {
        return brokerInfo.getPort();
    }
}
