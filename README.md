# demo: microservice-spring-cloud

## history:

### branch:step-01

Simple Spring Cloud ConfigServer + Client application


* [Spring Cloud Config Server](http://cloud.spring.io/spring-cloud-static/spring-cloud-config/1.4.3.RELEASE/single/spring-cloud-config.html)


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

### Client Root 
```
# fetch the eureka client 
$ curl localhost:8001/operation
```



