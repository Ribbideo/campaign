package com.kencorhealth.campaign.service;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.kencorhealth.campaign.cdn.CDNUtil;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.ProviderHandler;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.mongo.MongoHealthCheck;
import com.kencorhealth.campaign.mq.CMQFactory;
import com.kencorhealth.campaign.service.api.ApiResource;
import com.kencorhealth.campaign.service.api.auth.PostAuthenticator;
import com.kencorhealth.campaign.service.config.CampaignConfig;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.util.SortedMap;
import com.kencorhealth.campaign.service.common.CampaignConstants;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.servlets.CacheBustingFilter;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class Campaign extends Application<CampaignConfig>
    implements CampaignConstants {
    public static void main(String[] args) throws Exception {
        new Campaign().run(args);
    }

    @Override
    public String getName() {
        return CAMPAIGN;
    }

    @Override
    public void initialize(Bootstrap<CampaignConfig> bootstrap) {
        // Empty for now
    }

    @Override
    public void run(CampaignConfig cc, Environment e) throws Exception {
        e.servlets()
            .addFilter("CacheBustingFilter", new CacheBustingFilter())
            .addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST), true, "/*"
            );

        enableCorsHeaders(e);
        
        CampaignFactory.init(cc.getMongo().getUri());
        
        final MongoHealthCheck mhc =
            new MongoHealthCheck(ProviderHandler.class);
        HealthCheckRegistry hcr = e.healthChecks();
        hcr.register("Mongo", mhc);        
        
        final ApiResource api = new ApiResource();
        e.jersey().register(api);

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
        
        CMQFactory.init(cc);
        
        CDNUtil.init(cc.getS3());
        
        JerseyEnvironment je = e.jersey();
        
        je.register(
            new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder()
                    .setAuthenticator(new PostAuthenticator())
                    .setPrefix("Bearer")
                    .buildAuthFilter()
            )
        );
        
        je.register(MultiPartFeature.class);

        je.register(
            new AuthValueFactoryProvider.Binder(AuthToken.class)
        );
    }
    
    private void enableCorsHeaders(Environment e) {
        final FilterRegistration.Dynamic corsFilter = e.servlets().addFilter("CORS", CrossOriginFilter.class);
        corsFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
                "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        corsFilter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
    }
}
