package de.javamark.springcloud.controller;

import de.javamark.springcloud.feign.OperandFeignInterface;
import de.javamark.springcloud.feign.OperatorFeignInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Controller
public class RequestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    private final OperandFeignInterface operandFeignInterface;
    private final OperatorFeignInterface operatorFeignInterface;

    @Autowired
    public RequestController(OperandFeignInterface operandFeignInterface, OperatorFeignInterface operatorFeignInterface) {
        this.operandFeignInterface = operandFeignInterface;
        this.operatorFeignInterface = operatorFeignInterface;
    }

    @GetMapping("/operation")
    public @ResponseBody
    String getOperation() throws ScriptException {
        String function = operandFeignInterface.getOperand() + " "
                + operatorFeignInterface.getOperator() + " "
                + operandFeignInterface.getOperand();
        LOGGER.info("[+] function {}", function);
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        String result = "" + scriptEngine.eval(function);
        LOGGER.info("[+] result {}", result);
        return function + " = " + result;
    }
}
