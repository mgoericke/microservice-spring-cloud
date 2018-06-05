package de.javamark.springcloud.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-client-operator")
public interface OperatorFeignInterface {

    @GetMapping("/")
    String getOperator();
}
