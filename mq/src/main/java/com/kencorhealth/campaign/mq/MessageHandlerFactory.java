package com.kencorhealth.campaign.mq;

public class MessageHandlerFactory {
    private static MessageHandlerFactory instance;

    public static synchronized MessageHandlerFactory get() {
        if (instance == null) {
            instance = new MessageHandlerFactory();
        }

        return instance;
    }

    public MessageHandler getMessageHandler(
        Class<? extends MessageHandler> implClass) throws Exception {
        return implClass.newInstance();
    }
}
