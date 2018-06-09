package de.javamark.springcloud.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Primary
@RefreshScope
public class DocumentationController implements SwaggerResourcesProvider {
    private final DiscoveryClient discoveryClient;

    @Value("${zuul.prefix}")
    private String zuulPrefix;

    @Autowired
    public DocumentationController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public List<SwaggerResource> get() {
        final List<String> services = discoveryClient.getServices();
        Collections.sort(services);
        final List<SwaggerResource> resources = new ArrayList<SwaggerResource>();
        for (String service : services) {
            resources.add(swaggerResource(service, zuulPrefix + "/" + service + "/v2/api-docs"));
        }
        return resources;
    }


    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
