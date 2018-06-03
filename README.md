# demo: microservice-spring-cloud

A Simple Spring Cloud ConfigServer + Client application

## ConfigServer

### Maven

The ConfigServer uses the following dependency which includes all necessary logic to setup and run a spring-cloud-config-server.

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

### Properties

ConfigServer reads configuration files from a github repository that is configured in application.properties. 

```
# spring cloud config
spring.cloud.config.server.git.uri=https://github.com/mgoericke/microservice-spring-cloud/
spring.cloud.config.server.git.search-paths=configuration
```

The server listens on port 8001 to avoid conflicts with other locally running services.

```
# server config
server.port=8001
```


## Client

### Maven

The only dependency that is important (for the moment):

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

It add all necessary beans to the spring context and allows us to read properties from the config server.

### Properties

The client is configured to receive properties from the ConfigServer. The configuration how to connect to the ConfigServer is stored in bootstrap.properties

```
spring.profiles.active=test
spring.application.name=javamark-client
spring.cloud.config.uri=http://localhost:8001
spring.cloud.config.fail-fast=true
```