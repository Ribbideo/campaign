package com.kencorhealth.campaign.mq;

public class MQFactory implements AMQPProvider, CMQConstants {
    private static MQFactory instance;

    private final BrokerInfo brokerInfo;

    private MQFactory(BrokerInfo brokerInfo) {
        this.brokerInfo = brokerInfo;
    }

    public static MQFactory init(BrokerInfo brokerInfo) {
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
