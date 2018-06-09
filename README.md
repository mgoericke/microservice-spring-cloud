# 07 - Zuul - API Gateway

(And swagger)
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

## Optional

since Zuul does'nt support Protocol Translation, clients are responsible to translate protocols. For example:

* Spring Integration (very complex)
* Apache Camel - multiple adapters including an adapter for Spring Integration 

##  Zuul

Create a new submodule `zuul-server` and add the `spring-cloud-starter-zuul` dependency to the pom:

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zuul</artifactId>
</dependency>
```

Also, add the `spring-cloud-starter-eureka` dependency to the zuul-server module (to help the zuul-server to find the services).

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-eureka</artifactId>
</dependency>
```

Annotate the main class `ZuulServer.java` with `@EnableZuulProxy` and `@EnableDiscoveryClient`.

```
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ZuulServer {
    public static void main(String[] args ) {
        SpringApplication.run(ZuulServer.class, args);
    }
    
    /**
    * provide ETag header caching because Zuul doesn't support caching.
    */
    @Bean
    public Filter shallowETagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
```

Check the `zuul.properties` for the property `zuul.prefix=/calc` -> all service requests will be prefixed with the value of `zuul.prefix`.

## How to start

Simply checkout the repository, build the applications and start all services ...

```
# clone (optionally switch the branch)
$ https://github.com/mgoericke/microservice-spring-cloud.git
```

Start Zookeeper ...
```
$ ./bin/zookeeper-server-start.sh ./config/zookeeper.properties
```

Start Kafka Server ...
```
$ ./bin/kafka-server-start.sh ./config/server.properties
```

```
# build and start applications
$ ./startup.sh
```

## What to test :)

### Reverse Proxy

Zuul acts as an reverse proxy and routes a request to an actual service. Services can be excluded in configuration.

```
http://{zuul-host}:{zuul-port}/{eureka-service-id}/{service-path} -> curl {service-host}:{service-port}/{service-path} 
```

* zuul accepts the request
* zuul resolves the service (using eureka)
* zuul routes the request to the resolved service without exposing the actual service url, port ..

curl localhost:8080/eureka-client-operand/info -> curl localhost:{service-port}/info


### Access Eureka Server to check if all services work as expected 
```
# get the ui of eureka server
$ curl localhost:8010/
```

## Swagger support

Add Swagger support (Spring Fox) to client-request, client-response, zuul-server.

* Zuul Swagger: `http://localhost:8080/swagger-ui.html#`
* Operation Endpoint over Zuul: `http://localhost:8080/calc/operation`

### Maven
```
<!-- Rest Dependencies-->
<!-- Swagger Reachable at http://localhost:8080/swagger-ui.html and http://localhost:8080/v2/api-docs.
Generate sources with swagger-codegen. Downloadable from wget https://oss.sonatype.org/content/repositories/releases/io/swagger/swagger-codegen-cli/2.2.1/swagger-codegen-cli-2.2.1.jar.
Call on CLI: java -jar swagger-codegen-cli-2.2.1.jar generate -l java -i http://localhost:8080/v2/api-docs-->
<!-- Enables the  http://{host}:{port}/v2/api-docs -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>${swagger.version}</version>
</dependency>

<!-- Enables the UI under http://{host}:{port}/swagger-ui.html -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>${swagger.version}</version>
</dependency>
```

### Java classes

#### Swagger generation configuration

Add the swagger configuration to the `client-request` module. 
Using this configuration the swagger endpoint will only expose REST endpoints located in the package `de.javamark`

```
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("default")
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.javamark"))
                .build();
    }
}
```

#### Enable Swagger generation

Add the annotation `@EnableSwagger2` to the main classes in client-response, client-request and zuul-server. 

