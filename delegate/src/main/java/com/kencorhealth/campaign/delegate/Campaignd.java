package com.kencorhealth.campaign.delegate;

import com.kencorhealth.campaign.mq.AppDelegate;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.ProviderHandler;
import com.kencorhealth.campaign.mongo.MongoHealthCheck;
import com.kencorhealth.campaign.delegate.config.CampaigndConfig;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.util.SortedMap;
import com.kencorhealth.campaign.delegate.common.CampaigndConstants;
import com.kencorhealth.campaign.delegate.receiver.MQExecCampaignIVRReceiver;
import com.kencorhealth.campaign.delegate.receiver.MQExecCampaignSMSReceiver;
import com.kencorhealth.campaign.delegate.receiver.MQCampaignStageDataReceiver;
import com.kencorhealth.campaign.mq.CMQConstants;
import com.kencorhealth.campaign.mq.MessageHandler;
import java.util.Map;

public class Campaignd extends AppDelegate<CampaigndConfig>
    implements CampaigndConstants, CMQConstants {
    public static void main(String[] args) throws Exception {
        new Campaignd().run(args);
    }

    @Override
    public String getName() {
        return CAMPAIGND;
    }

    @Override
    public void initialize(Bootstrap<CampaigndConfig> bootstrap) {
        // Empty for now
    }

    @Override
    public void run(CampaigndConfig cc, Environment e) throws Exception {
        CampaignFactory.init(cc.getMongo().getUri());
        
        final MongoHealthCheck mhc =
            new MongoHealthCheck(ProviderHandler.class);
        HealthCheckRegistry hcr = e.healthChecks();
        hcr.register("Mongo", mhc);        
        
        // Ensure all config are okay at startup
        SortedMap<String, HealthCheck.Result> checks = hcr.runHealthChecks();

        checks.forEach((k, v) -> {
            if (!v.isHealthy()) {
                String message =
                    "Configuration " + k + " not healthy at startup";
                System.err.println(message);
                System.exit(-1);
            }
        });
        
        super.run(cc, e);
    }

    @Override
    public final void populateRoutingDataMap(
        Map<String, Class<? extends MessageHandler<?>>> routingDataMap) {
        routingDataMap.put(EXEC_CAMPAIGN_SMS,
            MQExecCampaignSMSReceiver.class
        );

        routingDataMap.put(EXEC_CAMPAIGN_IVR,
            MQExecCampaignIVRReceiver.class
        );

        routingDataMap.put(SUBMIT_CAMPAIGN_STAGE_DATA,
            MQCampaignStageDataReceiver.class
        );
    }

    @Override
    public final boolean isRedispatcher() {
        return false;
    }
}
