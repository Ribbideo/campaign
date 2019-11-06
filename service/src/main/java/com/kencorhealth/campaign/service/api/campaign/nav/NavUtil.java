package com.kencorhealth.campaign.service.api.campaign.nav;

import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.ngin.ScriptUtil;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Executor;
import com.kencorhealth.campaign.dm.common.Script;
import com.kencorhealth.campaign.dm.common.Transformable;
import com.kencorhealth.campaign.dm.common.Transformer;
import com.kencorhealth.campaign.dm.delegate.CampaignDispatcher;
import com.kencorhealth.campaign.dm.delivery.nav.ProcessorBased;
import com.kencorhealth.campaign.dm.delivery.script.ScriptInput;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.mq.CMQFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

class NavUtil {
    static <T> Executor<T> getExecutor() {
        return new Executor<T>() {
            @Override
            public T execute(
                ScriptInput scriptInput,
                Object... arg) throws CampaignException {
                ProcessorBased pb =
                    CampaignUtil.extractFromData(ProcessorBased.class, arg);
                
                Script script = pb.getScript();
                
                return (T) ScriptUtil.invoke(script, scriptInput);
            }

            @Override
            public CampaignDispatcher getDispatcher() {
                return CMQFactory.getDispatcher();
            }
        };
    }
    
    static void transform(Transformable t, AuthToken at)
        throws CampaignException {
        Map<String, Object> map = new HashMap();
        map.put(at.alias(), at);
        
        Transformer transformer = (String input) -> {
            String retVal = null;
            
            if (CampaignUtil.valid(input)) {
                try {
                    Template template =
                        new Template(
                            "tmpTemplate",
                            new StringReader(input),
                            config
                        );
                    Writer w = new StringWriter();
                    template.process(map, w);
                    retVal = w.toString();
                } catch (Exception e) {
                    throw new CampaignException(e);
                }
            }
            
            return retVal;
        };
        
        t.transformFrom(transformer);
    }

    static {
        config = new Configuration(Configuration.VERSION_2_3_26);
    }
    
    private static final Configuration config;
}
