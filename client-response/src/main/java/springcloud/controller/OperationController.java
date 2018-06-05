package springcloud.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@Profile("operator")
public class OperationController {

    @GetMapping("/")
    public @ResponseBody
    String getOperation() {
        final List<String> operationList = Arrays.asList("+", "-", "*", "/");
        int element = ThreadLocalRandom.current().nextInt(operationList.size());
        return  operationList.get(element);
    }
}
