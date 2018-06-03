package de.javamark.springcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PrintConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintConfig.class);

    private final String clientMode;

    public PrintConfig(@Value("${client.mode}") String clientMode) {
        this.clientMode = clientMode;
    }

    @PostConstruct
    public void printConfig() {
        LOGGER.info("[+] clientMode {}", clientMode);
    }
}
