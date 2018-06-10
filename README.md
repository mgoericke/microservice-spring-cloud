# 01 - Simple Spring Cloud ConfigServer + Client application

Spring Cloud Config Server provides an HTTP resource-based API for external configuration (name-value pairs or equivalent YAML content). 
The server is embeddable in a Spring Boot application, by using the @EnableConfigServer annotation.

* [Spring Cloud Config Server](http://cloud.spring.io/spring-cloud-static/spring-cloud-config/1.4.3.RELEASE/single/spring-cloud-config.html)


# ConfigServer Configuration

## Maven Dependencies

Add the `spring-cloud-config-server` dependency to the pom.

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

## @EnableConfigServer

Enable the ConfigServer 

```
@SpringBootApplication
@EnableConfigServer
public class Server {
    public static void main(String[]args) {
        SpringApplication.run(Server.class, args);
    }
}
```

## appplication.properties

Tell the ConfigServer where the properties shouold be load from and which port the ConfigServer is listening.

```
# spring cloud config
spring.cloud.config.server.git.uri=https://github.com/mgoericke/microservice-spring-cloud/
spring.cloud.config.server.git.search-paths=configuration

# server config
server.port=8001
```


# Client Configuration

## Maven

Ad the `pring-cloud-starter-config` to the pom.

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

## bootstrap.properties in client

Define the url of the ConfigServer, the current profile, application name ..

```
spring.profiles.active=dev
spring.application.name=javamark-client
spring.cloud.config.uri=http://localhost:8001
spring.cloud.config.fail-fast=true
```

# How to start

Simply checkout the repository, build the applications and start all services ...

```
# clone (switch the branch)
$ git clone https://github.com/mgoericke/microservice-spring-cloud.git
$ git checkout origin/step-01

# build and start applications
$ ./startup.sh
```

# What to test?

## Config Server

```
# fetch the client properties from the config server (eureka-client-root)
$ curl http://localhost:8001/javamark-client/dev
```

reponds with: 

```
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Date: Sun, 10 Jun 2018 20:48:44 GMT
Transfer-Encoding: chunked
X-Application-Context: application:8001

{
    "label": null, 
    "name": "javamark-client", 
    "profiles": [
        "dev"
    ], 
    "propertySources": [
        {
            "name": "https://github.com/mgoericke/microservice-spring-cloud/configuration/javamark-client-dev.properties", 
            "source": {
                "client.mode": "javamark-client-dev"
            }
        }, 
        {
            "name": "https://github.com/mgoericke/microservice-spring-cloud/configuration/javamark-client.properties", 
            "source": {
                "client.mode": "javamark-client"
            }
        }, 
        {
            "name": "https://github.com/mgoericke/microservice-spring-cloud/configuration/application.properties", 
            "source": {
                "client.mode": "application"
            }
        }
    ], 
    "state": null, 
    "version": "eeddf59d7bbb9f8b085a01a7f7ac1b2c30a10903"
}

```

## Client 

Check the log file since the client logs a message to standard out. the value of the property comes from the 
config server. Since the property `spring.profiles.active` in client's `bootstrap.properties` is set to `dev`, the value reads `javamark-client-dev`

```
de.javamark.springcloud.PrintConfig : [+] clientMode javamark-client-dev
```

The ConfigServer resolves the value in the following order:

* `client.mode=application` from application.properties
* `client.mode=javamark-client` from javamark-client.properties
* `client.mode=javamark-client-dev` from javamark-client-dev.properties



