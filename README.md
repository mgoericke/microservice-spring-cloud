# 06 - Spring Cloud Bus

Spring Cloud Bus links nodes of a distributed system with a lightweight message broker. This can then be used to *broadcast* state changes (e.g. *configuration changes*) or other management instructions. The only implementation currently is with an AMQP broker as the transport, but the same basic feature set (and some more depending on the transport) is on the roadmap for other transports.

[https://cloud.spring.io/spring-cloud-bus/](https://cloud.spring.io/spring-cloud-bus/)

## Project ToC
* [Step-01 - Spring Cloud Config Server](https://github.com/mgoericke/microservice-spring-cloud/tree/step-01)
* [Step-02 - Spring Cloud Eureka - Service Discovery](https://github.com/mgoericke/microservice-spring-cloud/tree/step-02)
* [Step-03 - Spring Cloud Ribbon - Client side loadbalancing](https://github.com/mgoericke/microservice-spring-cloud/tree/step-03)
* [Step-04 - Spring Cloud Feign - A declarative RESTClient](https://github.com/mgoericke/microservice-spring-cloud/tree/step-04)
* [Step-05 - Spring Cloud Hystrix & Spring Cloud Turbine - Circuit Breaker and Dashboard](https://github.com/mgoericke/microservice-spring-cloud/tree/step-05)
 



# What do we need here?

* Apache Kafka 
* Spring Cloud Bus dependency
* Spring Cloud Config Monitoring dependency
* Annotate Controller with @RefreshScope

## Install Apache Kafka

Download and extract Apache Kafka: [kafka_2.11-1.1.0.tgz](https://www.apache.org/dyn/closer.cgi?path=/kafka/1.1.0/kafka_2.11-1.1.0.tgz)

Start Zookeeper ...
```
$ ./bin/zookeeper-server-start.sh ./config/zookeeper.properties
```

Start Kafka Server ...
```
$ ./bin/kafka-server-start.sh ./config/server.properties
```

##  Spring Cloud Bus

add the `spring-cloud-starter-bus-kafka` dependency to all modules:

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-kafka</artifactId>
</dependency>
```

and the `spring-cloud-starter-config-monitor` dependency to the config server module.

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-monitor</artifactId>
</dependency>
```

## Annotate Controller

Annotate `OperatorController` in module `client-response` with `@RefreshScope`.

## How to start

Simply checkout the repository, build the applications and start all services ...

```
# clone (optionally switch the branch)
$ https://github.com/mgoericke/microservice-spring-cloud.git

# build and start applications
$ ./startup.sh
```

## What to test :)


### Access Operation Endpoint
```
# fetch theoperation endpoint - the operators defined in property 'operator.list' will appear
$ curl localhost:8001/operation
```

### Change a property
change property `operator.list` in `./configuration/eureka-client-operator.properties`. for example, remove + and *. 

* save file
* commit file
* push file to github


### Refresh the property (force a reload)
open a terminal and refresh the configuration - force a reload of the configuration:

```
curl -X POST http://localhost:8001/bus/refresh 
```

### and check the operation endpoint again. 
```
# fetch theoperation endpoint - only the operators defined in property 'operator.list' will appear 
$ curl localhost:8001/operation
```

### Access Config Server

```
# fetch the client properties from the config server (eureka-client-root)
$ curl localhost:8001/eureka-client/root
```

### Access Eureka Server 
```
# get the ui of eureka server
$ curl localhost:8010/
```




