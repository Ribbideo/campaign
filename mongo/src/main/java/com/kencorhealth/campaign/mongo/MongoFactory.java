package com.kencorhealth.campaign.mongo;

import com.kencorhealth.campaign.mongo.handler.MongoHandler;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.lang.reflect.Constructor;

public class MongoFactory {
    public static void init(String mongoUri) {
        if (mc == null) {
            mc = new MongoClient(new MongoClientURI(mongoUri));
        }
    }
    
    public static <MH extends MongoHandler> MH get(Class<MH> handlerClass) {
        MH retVal = null;
        
        try {
            String packageName = handlerClass.getPackage().getName();
            String implClassName = handlerClass.getSimpleName() + "Impl";
            Class implClass =
                Class.forName(packageName + ".impl." + implClassName);
            Constructor<MH> ctor =
                implClass.getConstructor(MongoClient.class);
            retVal = ctor.newInstance(mc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return retVal;
    }
    
    private static MongoClient mc;
}
