package com.kencorhealth.campaign.dm.config;

public class BrokerConfig {
    private String host;
    private int port;

    public int reconnectInterval() {
        return 10;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "BrokerInfo{" + "host=" + host + ", port=" + port + '}';
    }
}
