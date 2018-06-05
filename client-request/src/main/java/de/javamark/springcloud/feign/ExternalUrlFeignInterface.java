package de.javamark.springcloud.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "externalUrlFeignInterface", url = "http://www.mocky.io/v2/5b16ad6f3100004e006f402e")
public interface ExternalUrlFeignInterface {

    @GetMapping("/external")
    Map<String, String> getExternal();
}
