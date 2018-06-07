# demo: microservice-spring-cloud

branch:step-06


# Spring Cloud Bus:
 
Spring Cloud Bus links nodes of a distributed system with a lightweight message broker. This can then be used to *broadcast* state changes (e.g. *configuration changes*) or other management instructions. The only implementation currently is with an AMQP broker as the transport, but the same basic feature set (and some more depending on the transport) is on the roadmap for other transports.

[https://cloud.spring.io/spring-cloud-bus/](https://cloud.spring.io/spring-cloud-bus/)


# What do we need here?

* Apache Kafka 
* Spring Cloud Bus dependency

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

## How to start

Simply checkout the repository, build the applications and start all services ...

```
# clone (optionally switch the branch)
$ https://github.com/mgoericke/microservice-spring-cloud.git

# build and start applications
$ ./startup.sh
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

### Access Eureka Client Root 
```
# fetch the eureka client 
$ curl localhost:8001/operation
```



