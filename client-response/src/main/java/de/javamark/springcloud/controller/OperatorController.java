package de.javamark.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@Profile("operator")
@RefreshScope
public class OperatorController {

    @Value("#{'${operator.list}'.split(',')}")
    private List<String> operationList;

    @GetMapping("/")
    public @ResponseBody
    String getOperation() {
        int element = ThreadLocalRandom.current().nextInt(operationList.size());
        return  operationList.get(element);
    }
}
