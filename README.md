# demo: microservice-spring-cloud

branch:step-06

#  Spring Cloud Bus

since I use a local Apache Kafka installation for the demo, I only added the `spring-cloud-starter-bus-kafka` dependency to all modules:

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-kafka</artifactId>
</dependency>
```





### How to start

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



