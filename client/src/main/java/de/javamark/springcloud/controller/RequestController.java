package de.javamark.springcloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.net.URI;
import java.util.List;

@Controller
public class RequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    private final DiscoveryClient client;

    @Autowired
    public RequestController(DiscoveryClient client) {
        this.client = client;
    }

    @GetMapping("/operation")
    public @ResponseBody
    String getOperation() throws ScriptException {
        String function = getPart("eureka-client-operand") + " "
                + getPart("eureka-client-operator") + " "
                + getPart("eureka-client-operand");

        LOGGER.info("[+] function {}", function);
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String result = "" + engine.eval(function);
        LOGGER.info("[+] result {}", result);
        return result;
    }

    private String getPart(String service) {

        // get service by name from discoveryClient
        List<ServiceInstance> list = client.getInstances(service);

        // if at least one service is available ..
        if (list != null && !list.isEmpty()) {
            // get the uri of the service
            URI uri = list.get(0).getUri();
            if (uri != null) {
                // and call the service
                LOGGER.info("[+] request service {} uri {}", service, uri.toString());
                return new RestTemplate().getForObject(uri, String.class);
            }
        }
        return null;
    }
}
