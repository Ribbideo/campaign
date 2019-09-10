package com.kencorhealth.campaign.http.incoming;

import com.kencorhealth.campaign.http.base.handler.HttpBasedHandler;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class CHIFactory {
    private static String baseUrl;
    private static String user;
    private static String password;
    private static String authToken;
    
    public static void init(Properties properties) {
        baseUrl = properties.getProperty("outreach.baseUrl");
        user = properties.getProperty("outreach.user");
        password = properties.getProperty("outreach.password");
    }
    
    public static <H extends HttpBasedHandler> H internal(
        Class<H> handlerClass) {
        return doGet(handlerClass, baseUrl, true);
    }

    public static <H extends HttpBasedHandler> H external(
        Class<H> handlerClass, String baseUrl) {
        return doGet(handlerClass, baseUrl, false);
    }
    
    private static <H extends HttpBasedHandler> H doGet(
        Class<H> handlerClass, String baseUrl, boolean internal) {
        H retVal = null;
        
        try {
            String packageName = handlerClass.getPackage().getName();
            String implClassName = handlerClass.getSimpleName() + "Impl";
            Class implClass =
                Class.forName(packageName + ".impl." + implClassName);
            Constructor<H> ctor = implClass.getConstructor();
            retVal = ctor.newInstance();
            retVal.setBaseUrl(baseUrl, internal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return retVal;
    }
    
    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getAuthToken() {
        return authToken;
    }
}
