package de.javamark.springcloud.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@FeignClient(
        name = "externalUrlFeignInterface",
        url = "http://www.mocky.io/v2/5b16ad6f3100004e006f402e",
        fallback = ExternalUrlFeignClientFallback.class)
public interface ExternalUrlFeignClient {

    @GetMapping("/external")
    Map<String, String> getExternal();
}

@Component
class ExternalUrlFeignClientFallback implements ExternalUrlFeignClient {

    @Override
    public Map<String, String> getExternal() {
        return new HashMap<>();
    }
}
