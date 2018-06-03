# demo: microservice-spring-cloud

A Simple Spring Cloud ConfigServer + Client application

## ConfigServer

The ConfigServer provides configuration for services. It understand profiles. 

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

The client read properties from a ConfigServer.

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
spring.profiles.active=dev
spring.application.name=javamark-client
spring.cloud.config.uri=http://localhost:8001
spring.cloud.config.fail-fast=true
```

## Read configuration

The configuration provided by the ConfigServer can also be requested with a simple HTTP GET (there is no security implemented since this is a demo)

The following http request (httpie) will read the configuration for an application (javamark-client) with a specific profile (dev)

* spring.profiles.active=dev
* spring.application.name=javamark-client

The client reads the full response and will find the correct value for the property *client.mode*. 

Finally, *client.mode* gets the value *javamark-client-dev* since the client read the response as follows:

1. application.properties - client.mode = application
2. javamark-client.properties - client.mode = javamark-client
3. javamark-client-dev.properties - client.mode = javamark-client-dev
 


```
$ http localhost:8001/javamark-client/dev
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
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
    "version": "45789d4ad77e3b7bc3859f2a14b82cb44b0fa546"
}

```