package com.kencorhealth.campaign.mq;

import com.kencorhealth.campaign.dm.config.BrokerConfig;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.json.JsonUtil;
import com.rabbitmq.client.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQUtil implements CMQConstants {
    private final static Logger log = LoggerFactory.getLogger(MQUtil.class);

    private static Connection getConnection(AMQPProvider provider)
        throws TimeoutException, IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(provider.getAMQPHostname());
        factory.setPort(provider.getAMQPPort());
        return factory.newConnection();
    }

    public static void sendMessage(
        Object data,
        String routingKey,
        AMQPProvider provider)
        throws CampaignException, TimeoutException, IOException {
        byte[] jsonAsBytes = null;

        String dataStr = null;

        if (data instanceof String) {
            dataStr = (String) data;
        } else if (data != null) {
            dataStr = JsonUtil.asJson(data);
        }

        if (dataStr != null) {
            log.debug(" [x] Sending '" + dataStr + "', key: " + routingKey);

            jsonAsBytes = dataStr.getBytes("UTF-8");

            sendMessage(routingKey, jsonAsBytes, true, provider);
        } else {
            log.debug("Empty data, nothing to send");
        }
    }

    public static boolean sendMessage(
        String routingKey,
        byte[] data,
        boolean persistForRetry,
        AMQPProvider provider)
        throws TimeoutException, IOException {
        boolean retVal = false;

        Connection connection = null;
        Channel channel = null;

        try {
            connection = getConnection(provider);

            channel = connection.createChannel();

            channel.exchangeDeclare(RD_EXCHANGE, "direct", true);

            channel.txSelect();

            channel.basicPublish(
                    RD_EXCHANGE,
                    routingKey,
                    true,
                    false,
                    MessageProperties.PERSISTENT_BASIC,
                    data);

            retVal = true;
        } catch (IOException e) {
            log.error(
                    "Unable to send message (reason: " + e.getMessage() + ")", e);
            throw new IOException(e);
        } finally {
            MQUtil.close(channel, retVal);
            MQUtil.close(connection);
        }

        return retVal;
    }

    public static void close(Connection c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ex) {
                log.warn("Could not close connection", ex);
            }
        }
    }

    public static void close(Channel c, boolean commit) {
        if (c != null) {
            try {
                if (commit) {
                    c.txCommit();
                } else {
                    c.txRollback();
                }
            } catch (IOException ex) {
                log.warn("Problem during commit/rollback", ex);
            } finally {
                try {
                    c.close();
                } catch (IOException | TimeoutException ex) {
                    log.warn("Could not close connection", ex);
                }
            }
        }
    }

    public static File validateMQServerConfigFile(String configFilePath) {
        File configFile = new File(configFilePath);

        if (!configFile.exists() || !configFile.canRead()) {
            String errorMessage
                    = "Configuration file missing or unreadable, quitting...";

            log.error(errorMessage);

            System.exit(-2);
        }

        return configFile;
    }

    public static QueueingConsumer getConsumer(
        BrokerConfig brokerInfo,
        String queueName,
        String[] routingKeys) throws Exception {
        QueueingConsumer retVal = null;

        String brokerHost = brokerInfo.getHost();
        int brokerPort = brokerInfo.getPort();

        Connection connection = getMQConnection(brokerHost, brokerPort);

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(RD_EXCHANGE, "direct", true);

        channel.queueDeclare(queueName, true, false, false, null);

        for (String bindKey : routingKeys) {
            channel.queueBind(queueName, RD_EXCHANGE, bindKey);
        }

        log.info(queueName + " waiting for messages...");

        retVal = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, retVal);

        return retVal;
    }

    private static Connection getMQConnection(String brokerHost, int brokerPort)
        throws TimeoutException, IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(brokerHost);
        factory.setPort(brokerPort);
        return factory.newConnection();
    }
}
