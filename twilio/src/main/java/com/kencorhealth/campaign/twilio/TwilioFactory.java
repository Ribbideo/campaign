package com.kencorhealth.campaign.twilio;

import com.twilio.Twilio;
import freemarker.template.Configuration;
import java.lang.reflect.Constructor;
import com.kencorhealth.campaign.twilio.handler.TwilioBasedHandler;

public class TwilioFactory {
    private static TwilioConfig twilioConfig;
    private static Configuration freeMarkerConfig;
    
    public static void init(TwilioConfigProvider tcp) {
        twilioConfig = tcp.getTwilio();
        
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
        freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_26);
    }

    public static TwilioConfig getTwilio() {
        return twilioConfig;
    }
    
    public static Configuration getFreeMarkerConfig() {
        return freeMarkerConfig;
    }

    public static <H extends TwilioBasedHandler> H get(Class<H> handlerClass) {
        H retVal = null;
        
        try {
            String packageName = handlerClass.getPackage().getName();
            String implClassName = handlerClass.getSimpleName() + "Impl";
            Class implClass =
                Class.forName(packageName + ".impl." + implClassName);
            Constructor<H> ctor = implClass.getConstructor();
            retVal = ctor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return retVal;
    }
}
