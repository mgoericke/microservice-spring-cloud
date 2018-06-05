package de.javamark.springcloud;

import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.turbine.EurekaInstanceDiscovery;
import org.springframework.cloud.netflix.turbine.TurbineProperties;

import java.util.List;

public class OwnCommonsInstanceDiscovery extends EurekaInstanceDiscovery {
    private static final Logger LOGGER = LoggerFactory.getLogger(OwnCommonsInstanceDiscovery.class);
    private final DiscoveryClient discoveryClient;


    public OwnCommonsInstanceDiscovery(TurbineProperties turbineProperties, EurekaClient eurekaClient, DiscoveryClient discoveryClient) {
        super(turbineProperties, eurekaClient);
        this.discoveryClient = discoveryClient;
    }


    @Override
    protected List<String> getApplications() {
        final List<String> services = discoveryClient.getServices();
        LOGGER.info("[+] Request new Services: " + services.size());
        return services;
    }
}

