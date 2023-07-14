# Cargoship demo Spring Boot Project

Cargoship demo is a Spring Boot(v3.1.1) demo project with the next features

* AOP Transaction Manager
* `@Behavior` annotation to use instead `@Service`
* Component Scan looking for `@Behavior` annotated classes
* Logging Aspect to log a blueprint when a method is called
* Basic Controller Advice for most common exceptions
* Mapstruct to mapp Domain Objects to Ports I/O**

* Web
* Distribute Tracing
* Actuator endpoints
* Data JPA+PostgresSQL
* Data MongoDB+MongoExpress
* Data Redis
* Cache manager with `@Cacheable` annotation
* Kafka  Cloud Stream
* HTTP Yac Scripts
* Docker-Compose 
* Schema Registry to publish AVRO,PROTO and JSON schemas

# Required
### Software

* Java JDK 17>=
* NodeJS (to use httpyac cli)

### IDE

* Set up code formatting template (root folder intellij-java-google-style.xml)
* Change properties encoding to UTF-8

### CLI

* HTTP Yac. See [HTTP Tests](HELP.md#http-tests) to install

### Container Run Time

* Recommended [Podman](https://podman.io/docs/installation) and [Podman Compose](https://github.com/containers/podman-compose)
* MacOs alternative [Colima](https://github.com/abiosoft/colima/tree/main)
```cli
brew install docker docker-compose colima
```

## Run

### Clean and run code
```cli
mvn clean install
mvn spring-boot:run
```

### Unit Tests
By default, the Surefire Plugin will automatically include all test classes with the following wildcard patterns:

- "**/Test*.java" - includes all of its subdirectories and all Java filenames that start with "Test".
- "**/*Test.java" - includes all of its subdirectories and all Java filenames that end with "Test".
- "**/*Tests.java" - includes all of its subdirectories and all Java filenames that end with "Tests".
- "**/*TestCase.java" - includes all of its subdirectories and all Java filenames that end with "TestCase".
```cli
mvn test
```

See reference [surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin/examples/inclusion-exclusion.html)

### Integration Tests
By default, the Failsafe Plugin will automatically include all test classes with the following wildcard patterns:

- "**/IT*.java" - includes all of its subdirectories and all Java filenames that start with "IT".
- "**/*IT.java" - includes all of its subdirectories and all Java filenames that end with "IT".
- "**/*ITCase.java" - includes all of its subdirectories and all Java filenames that end with "ITCase".
```cli
mvn integration-test
```

See reference [failsafe-plugin](https://maven.apache.org/surefire/maven-failsafe-plugin/examples/inclusion-exclusion.html)

### Clean and run code
```cli
mvn clean install
mvn spring-boot:build-image
```


### HTTP Tests
```cli
httpyac send http/* --all -o short
```
### Usefull metrics

* http.server.requests : See time spend and count of controller calls
* http.client.requests : See time spend and count of restTemplate/webclient requests
* spring.cloud.function : Time spend and count to produce and consume messages (kafka). Require `@Timed` in producer classes see `DeviceSecProducerAdapter` and a bean of type `TimedAspect` and `MicrometerConfiguration`
* spring.data.repository.invocations: Time spend and count 

Example to call a metric
```cli
http http://localhost:8090/actuator/metrics/spring.cloud.function
```
