package com.kencorhealth.campaign.http.rpm;

import com.kencorhealth.campaign.dm.config.RouterConfig;
import com.kencorhealth.campaign.http.rpm.handler.RpmBasedHandler;
import java.lang.reflect.Constructor;

public class RpmFactory {
    public static void init(RouterConfig router) {
        RpmFactory.router = router;
    }
    
    public static <H extends RpmBasedHandler> H get(Class<H> handlerClass) {
        H retVal = null;
        
        try {
            String packageName = handlerClass.getPackage().getName();
            String implClassName = handlerClass.getSimpleName() + "Impl";
            Class implClass =
                Class.forName(packageName + ".impl." + implClassName);
            Constructor<H> ctor = implClass.getConstructor();
            retVal = ctor.newInstance();
            retVal.setRouterConfig(router);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return retVal;
    }
    
    private static RouterConfig router;
}
