package de.javamark.springcloud.controller;

import de.javamark.springcloud.feign.ExternalUrlFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ExternalController {

    private final ExternalUrlFeignClient externalUrlFeignClient;

    @Autowired
    public ExternalController(ExternalUrlFeignClient externalUrlFeignClient) {
        this.externalUrlFeignClient = externalUrlFeignClient;
    }

    @GetMapping("/externalcall")
    public @ResponseBody
    Map<String, Object> getFromExternal() {
        Map<String, Object> result = new HashMap<>();
        result.put("external", this.externalUrlFeignClient.getExternal());
        result.put("timestamp", Instant.now().toString());
        return result;
    }
}
