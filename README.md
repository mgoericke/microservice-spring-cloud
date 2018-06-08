# 07 - Zuul - API Gateway

Routing is an integral part of a microservice architecture. For example, / may be mapped to your web application, /api/users is mapped to the user service and /api/shop is mapped to the shop service. Zuul is a JVM-based router and server-side load balancer from Netflix.

* [https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html](https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html)
* [https://github.com/Netflix/zuul/wiki](https://github.com/Netflix/zuul/wiki)



## Project ToC
* [Step-01 - Spring Cloud Config Server](https://github.com/mgoericke/microservice-spring-cloud/tree/step-01)
* [Step-02 - Spring Cloud Eureka - Service Discovery](https://github.com/mgoericke/microservice-spring-cloud/tree/step-02)
* [Step-03 - Spring Cloud Ribbon - Client side loadbalancing](https://github.com/mgoericke/microservice-spring-cloud/tree/step-03)
* [Step-04 - Spring Cloud Feign - A declarative RESTClient](https://github.com/mgoericke/microservice-spring-cloud/tree/step-04)
* [Step-05 - Spring Cloud Hystrix & Spring Cloud Turbine - Circuit Breaker and Dashboard](https://github.com/mgoericke/microservice-spring-cloud/tree/step-05)
* [Step-05 - Spring Cloud Bus - Broadcast configuration Changes](https://github.com/mgoericke/microservice-spring-cloud/tree/step-06)
 

# What do we need here?

* Zuul dependency
* Spring Cloud Eureka dependency
* Small code changes :wink:

##  Zuul

add the `spring-cloud-starter-` dependency to all modules:

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




