package com.kencorhealth.campaign.http.rpm;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.http.rpm.handler.RpmBasedHandler;
import java.lang.reflect.Constructor;

public class CHRFactory {
    public static <H extends RpmBasedHandler> H get(Class<H> handlerClass) {
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
    
    public static <H extends RpmBasedHandler> H get(
        Class<H> handlerClass, UrlInfo urlInfo) throws CampaignException {
        H retVal = get(handlerClass);

        retVal.setUrlInfo(urlInfo);
        
        return retVal;
    }
}
