# demo: microservice-spring-cloud

## history:

### branch:step-01

Simple Spring Cloud ConfigServer + Client application


* [Spring Cloud Config Server](http://cloud.spring.io/spring-cloud-static/spring-cloud-config/1.4.3.RELEASE/single/spring-cloud-config.html)


### branch:step-02

Service Discovery with Eureka Server, Spring Cloud Config Server + Client application

* [Eureka Docs](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html)
* [Eureka REST](https://github.com/Netflix/eureka/wiki/Eureka-REST-operations)

### branch:step-03

Client-side load balancing with Spring Ribbon

Service Discovery with Eureka Server, Spring Cloud Config Server + Client application

* [Ribbon Docs](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-ribbon.html)
* [Client Side Load Balancing](https://spring.io/guides/gs/client-side-load-balancing/)

### branch:step-04

Add Spring Cloud Feign support. Declarative REST client to call registered services (call services by name). 

* [Spring Cloud Feign](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html)


## How to start

Simply checkout the repository, build the applications and start all services ...

```
# clone (optionally switch the branch)
$ https://github.com/mgoericke/microservice-spring-cloud.git

# build and start applications
$ ./startup.sh
```

### Config Server

```
# fetch the client properties from the config server (eureka-client-root)
$ curl localhost:8001/eureka-client/root
```

### Eureka Server 
```
# get the ui of eureka server
$ curl localhost:8010/
```

### Eureka Client Root 
```
# fetch the eureka client 
$ curl localhost:8001/operation
```



