package de.javamark.springcloud.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-client-operand")
public interface OperandFeignInterface {

    @GetMapping("/")
    String getOperand();
}
