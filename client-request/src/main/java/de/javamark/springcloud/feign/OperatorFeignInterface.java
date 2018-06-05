package de.javamark.springcloud.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "eureka-client-operator", fallback = OperatorFeignInterfaceFallback.class)
public interface OperatorFeignInterface {

    @GetMapping("/")
    String getOperator();
}

@Component
class OperatorFeignInterfaceFallback implements OperatorFeignInterface {

    @Override
    public String getOperator() {
        return "*";
    }
}