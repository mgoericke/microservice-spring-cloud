package de.javamark.springcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    public static void main(String[] args) {
        LOGGER.info(" [+] client startup");
        SpringApplication.run(Client.class, args);
    }
}
