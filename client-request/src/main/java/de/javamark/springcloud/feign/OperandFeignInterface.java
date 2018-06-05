package de.javamark.springcloud.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "eureka-client-operand", fallback = OperandFeignInterfaceFallback.class)
public interface OperandFeignInterface {

    @GetMapping("/")
    @HystrixCommand(commandKey = "key", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds"
                    , value = "30000")})
    String getOperand();
}

@Component
class OperandFeignInterfaceFallback implements OperandFeignInterface {

    @Override
    public String getOperand() {
        return "1";
    }
}
