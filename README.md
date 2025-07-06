# sports-event-service
A RESTful API example for receiving sports events and publishing to kafka

## Installation & Run
```bash
# Download this project
https://github.com/SudharshiniN/sports-event-service
```

For building and running the application you need:

- [JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.sports.event.EventApplication` class from your IDE.

Alternatively you can use the gradle

```bash
# To build the app
cd sports-event-service
.\gradlew build

# To run unit test
.\gradlew test

# To start the app
.\gradlew bootRun

# API Endpoint : http://localhost:8080/events
```

## API

#### /events
* `GET` : Get all events

#### /events/status
* `POST` : Creates a event 

Sample JSON
{
    "eventId" : "111",
    "live" : true
}

#### /events/{eventId}
* `DELETE` : Remove an event

## Kafka
To start Kafka locally follow the below instructions

```bash
#To start kafka locally
cd kafka
docker-compose up -d

#Check if container is running
docker ps -a

#Create a topic in kafka
docker exec -it kafka-sport kafka-topics --create --topic live-sports-event-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

#List the topics in kafka
docker exec -it kafka-sport kafka-topics --list --bootstrap-server localhost:9092

#To view messages in kafka
docker exec -it kafka-sport kafka-console-consumer --bootstrap-server localhost:9092 --topic live-sports-event-topic --from-beginning

#Shutdown and delete images
docker-compose down -v
docker system prune -af
```

## Summary
* Spring boot framework is used to build the rest api. Multiple endpoints are provided to add, delete and get events which is useful to test the services.
* Have used Concurrenthashmap for in memory cache since its a simple test application.
* Scheduler duration is configurable in application.properties. To keep it simple added a new endpoint to act as a external service.
* Rest template is used to connect to the external api for simplified HTTP request handling and easy integration with Spring.
* Docker setup file provided to setup and start kafka locally.


## Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.3/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.3/gradle-plugin/packaging-oci-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.3/reference/web/servlet.html)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/3.5.3/reference/messaging/kafka.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
