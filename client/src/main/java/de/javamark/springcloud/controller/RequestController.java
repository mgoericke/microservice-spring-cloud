package de.javamark.springcloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.net.URI;

@Controller
public class RequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    private final LoadBalancerClient loadBalancerClient;

    @Autowired
    public RequestController(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
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
        return function + " = " + result;
    }

    private String getPart(String service) {

        ServiceInstance instance = loadBalancerClient.choose(service);
        if (instance != null && instance.getUri() != null) {
            URI uri = instance.getUri();
            LOGGER.info("[+] request load balanced service: {} with uri {}", service, uri.toString());
            return new RestTemplate().getForObject(uri, String.class);
        }
        return null;
    }
}
