package com.kencorhealth.campaign.ngin;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.common.Script;
import com.kencorhealth.campaign.dm.delivery.script.ScriptInput;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.mongo.handler.MongoHandler;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.reflections.Reflections;
import com.kencorhealth.campaign.http.base.handler.HttpBasedHandler;
import com.kencorhealth.campaign.http.rpm.CHRFactory;
import com.kencorhealth.campaign.http.rpm.handler.RpmBasedHandler;

public class ScriptUtil {
    private static final ScriptEngine ENGINE;
    private static final Map<String, MongoHandler> DB_HANDLERS;
    private static final Map<String, HttpBasedHandler> RPM_HANDLERS;

    public static Object invoke(Script script, ScriptInput scriptInput)
        throws CampaignException {
        Object retVal = null;
        
        try {
            ENGINE.eval(new StringReader(script.getContents()));

            Invocable invocable = (Invocable) ENGINE;
            
            Map<String, Object> input = new HashMap();
            
            input.put("campaign.script", scriptInput);
            input.put("campaign.handler.db", DB_HANDLERS);
            input.put("campaign.handler.http.rpm", RPM_HANDLERS);
            
            retVal = invocable.invokeFunction("execute", input);
        } catch (Exception e) {
            throw new CampaignException(e);
        }

        return retVal;
    }

    static {
        ENGINE = new ScriptEngineManager().getEngineByName("nashorn");
        DB_HANDLERS = new HashMap();
        RPM_HANDLERS = new HashMap();

        Object[] packages = {
            "com.kencorhealth.campaign.db",
            "com.kencorhealth.campaign.http.prm"
        };

        Reflections reflections = new Reflections(packages);
        Set<Class<?>> handlerTypes =
            reflections.getTypesAnnotatedWith(Exportable.class);

        handlerTypes.forEach((type) -> {
            if (type.isInterface()) {
                if (MongoHandler.class.isAssignableFrom(type)) {
                    Class<? extends MongoHandler> dbHandlerType =
                        (Class<? extends MongoHandler>) type;
                    MongoHandler handler = CampaignFactory.get(dbHandlerType);
                    DB_HANDLERS.put(handler.alias(), handler);
                } else if (RpmBasedHandler.class.isAssignableFrom(type)) {
                    Class<? extends RpmBasedHandler> rpmHandlerType =
                        (Class<? extends RpmBasedHandler>) type;
                    RpmBasedHandler handler = CHRFactory.get(rpmHandlerType);
                    RPM_HANDLERS.put(handler.alias(), handler);
                }
            }
        });
    }
}
