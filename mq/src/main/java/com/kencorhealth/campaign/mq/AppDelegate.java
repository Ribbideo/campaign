package com.kencorhealth.campaign.mq;

import com.kencorhealth.campaign.dm.config.BrokerConfig;
import com.rabbitmq.client.QueueingConsumer;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AppDelegate<C extends Configuration>
    extends Application<C> {
    private final static Logger log =
        LoggerFactory.getLogger(AppDelegate.class);
    private QueueingConsumer consumer;
    private BrokerConfig brokerInfo;
    private Map<String, Class<? extends MessageHandler<?>>> routingDataMap;

    @Override
    public void run(C config, Environment e) throws Exception {
        BrokerConfigProvider bcp = (BrokerConfigProvider) config;
        
        CMQFactory.init(bcp);

        brokerInfo = bcp.getBroker();
        
        initBroker();
    }

    private int getReconnectInterval() {
        return brokerInfo.reconnectInterval();
    }

    protected void initBroker() throws Exception {
        routingDataMap = new HashMap();

        populateRoutingDataMap(routingDataMap);

        tryReconnect();
    }

    private void listenForMessages() {
        try {
            String queueName = getQueueName();

            consumer =
                MQUtil.getConsumer(
                    brokerInfo,
                    queueName,
                    routingDataMap.keySet().toArray(new String[0])
                );

            while (true) {
                QueueingConsumer.Delivery delivery = null;

                try {
                    delivery = consumer.nextDelivery();
                    processDelivery(delivery);
                } catch (Exception ex) {
                    log.error("Unable to process delivery", ex);
                    consumer = null;
                    tryReconnect();
                }
            }
        } catch (Exception e) {
            String message =
                "Could not establish connection with broker, will retry in " +
                getReconnectInterval() + " second(s)";
            log.error(message, e);
        }
    }

    private void tryReconnect() {
        while (true) {
            try {
                listenForMessages();
                Thread.sleep(getReconnectInterval() * 1000);
            } catch (InterruptedException ex) {
                // Ignore it
            }
        }
    }

    private void processDelivery(QueueingConsumer.Delivery delivery) {
        String routingKey = delivery.getEnvelope().getRoutingKey();
        String json = null;

        byte[] body = delivery.getBody();

        if (body != null) {
            json = new String(body);
        }

        Class<? extends MessageHandler<?>> messageHandlerClass =
            routingDataMap.get(routingKey);

        String processor = getClassName(messageHandlerClass);

        log.debug(
            " [x] Received '" + json + "', key: " + routingKey
            + ", processor: " + processor
        );
        if (messageHandlerClass != null) {
            processFromHandler(messageHandlerClass, json);
        } else {
            String message = "Did not find handler class for routing key "
                    + routingKey + ", data (" + json + ")";
            log.error(message);
        }
    }

    private String getClassName(Class<?> cls) {
        String retVal = null;

        if (cls != null) {
            retVal = cls.getName();
        }

        return retVal;
    }

    protected void processFromHandler(
            final Class<? extends MessageHandler<?>> messageHandlerClass,
            final String json) {
        log.debug("# Processing for " + messageHandlerClass.getSimpleName());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.debug("# Running task " + messageHandlerClass.getSimpleName());
                MessageHandler<?> messageHandler = null;

                try {
                    MessageHandlerFactory mhf = MessageHandlerFactory.get();
                    messageHandler = mhf.getMessageHandler(messageHandlerClass);
                    messageHandler.setJSON(json);
                    messageHandler.process();

                    String message =
                        "Processor " + getClassName(messageHandlerClass) +
                        " processed successfully";

                    log.debug(message);
                } catch (Exception e) {
                    String message =
                        "Processor " + getClassName(messageHandlerClass) +
                        " failed processing";

                    log.error(message, e);
                }
            }
        };

        try {
            runnable.run();
        } catch (Throwable e) {
            log.error("Unable to run task", e);
        }
    }

    protected abstract void populateRoutingDataMap(
        Map<String, Class<? extends MessageHandler<?>>> routingDataMap);

    protected String getQueueName() {
        return getName().toUpperCase() + "_QUEUE";
    }

    protected abstract boolean isRedispatcher();
}
