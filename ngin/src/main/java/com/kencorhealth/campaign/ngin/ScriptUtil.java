package com.kencorhealth.campaign.ngin;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.MemberHandler;
import com.kencorhealth.campaign.db.handler.ProviderHandler;
import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Script;
import com.kencorhealth.campaign.dm.delivery.script.ScriptInput;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.provider.Member;
import com.kencorhealth.campaign.mongo.handler.MongoHandler;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.reflections.Reflections;
import com.kencorhealth.campaign.http.rpm.CHRFactory;
import com.kencorhealth.campaign.http.rpm.UrlInfo;
import com.kencorhealth.campaign.http.rpm.handler.RpmBasedHandler;
import com.kencorhealth.campaign.pdf.PdfFactory;
import com.kencorhealth.campaign.pdf.handler.PdfBasedHandler;
import com.kencorhealth.campaign.twilio.TwilioFactory;
import com.kencorhealth.campaign.twilio.handler.TwilioBasedHandler;

public class ScriptUtil {
    private static final Map<String, TwilioBasedHandler> TWILIO_HANDLERS;
    private static final Map<String, MongoHandler> DB_HANDLERS;
    private static final Map<String, RpmBasedHandler> RPM_HANDLERS;
    private static final Map<String, PdfBasedHandler> PDF_HANDLERS;

    public static Object invoke(Script script, ScriptInput scriptInput)
        throws CampaignException {
        Object retVal = null;
        
        try {
            ScriptEngine se = engine();
            se.eval(new StringReader(script.getContents()));

            Invocable invocable = (Invocable) se;
            
            Map<String, Object> input = new HashMap();
            
            input.put("campaign.helper", new Helper());
            input.put("campaign.script", scriptInput);
            input.put("campaign.handler.db", DB_HANDLERS);
            input.put("campaign.handler.twilio", TWILIO_HANDLERS);
            input.put("campaign.handler.pdf", PDF_HANDLERS);
            input.put(
                "campaign.handler.http.rpm",
                rpmHandlers(scriptInput.getContext().getAuthToken())
            );
            
            retVal = invocable.invokeFunction("execute", input);
        } catch (Exception e) {
            throw new CampaignException(e);
        }

        return retVal;
    }
    
    private static Map<String, RpmBasedHandler> rpmHandlers(AuthToken at)
        throws Exception {
        Map<String, RpmBasedHandler> retVal = new HashMap();
        
        try (ProviderHandler ph = CampaignFactory.get(ProviderHandler.class);
             MemberHandler mh = CampaignFactory.get(MemberHandler.class)) {
            String baseUrl =
                ph.findById(at.getProviderId()).getBaseUrl() + "/rx/api";
            Member member = mh.findById(at.getUserId());
            String password =
                CampaignUtil.crypter().decrypt(
                    (String) member.getExtra().get("password"),
                    member
                );
            
            UrlInfo ui = new UrlInfo();
            ui.setBaseUrl(baseUrl);
            ui.setUserName(member.getPhoneNumber());
            ui.setPassword(password);
            
            for (String key: RPM_HANDLERS.keySet()) {
                try {
                    RpmBasedHandler handler =
                        RPM_HANDLERS.get(key)
                        .getClass().getConstructor().newInstance();
                    handler.setUrlInfo(ui);

                    retVal.put(key, handler);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        return retVal;
    }
    
    private static ScriptEngine engine() {
        return new ScriptEngineManager().getEngineByName("nashorn");
    }

    static {
       // ENGINE = new ScriptEngineManager().getEngineByName("nashorn");
        DB_HANDLERS = new HashMap();
        TWILIO_HANDLERS = new HashMap();
        RPM_HANDLERS = new HashMap();
        PDF_HANDLERS = new HashMap();

        Object[] packages = {
            "com.kencorhealth.campaign.db",
            "com.kencorhealth.campaign.twilio",
            "com.kencorhealth.campaign.http.rpm",
            "com.kencorhealth.campaign.pdf"
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
                } else if (TwilioBasedHandler.class.isAssignableFrom(type)) {
                    Class<? extends TwilioBasedHandler> tbh =
                        (Class<? extends TwilioBasedHandler>) type;
                    TwilioBasedHandler handler = TwilioFactory.get(tbh);
                    TWILIO_HANDLERS.put(handler.alias(), handler);
                } else if (RpmBasedHandler.class.isAssignableFrom(type)) {
                    Class<? extends RpmBasedHandler> rpmHandlerType =
                        (Class<? extends RpmBasedHandler>) type;
                    RpmBasedHandler handler = CHRFactory.get(rpmHandlerType);
                    RPM_HANDLERS.put(handler.alias(), handler);
                } else if (PdfBasedHandler.class.isAssignableFrom(type)) {
                    PdfBasedHandler handler = PdfFactory.get();
                    PDF_HANDLERS.put(handler.alias(), handler);
                }
            }
        });
    }
}
