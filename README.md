# Task Processor service

## Requirements

- Java 17
- Docker Compose (for running PostgreSQL)
- Maven

## Exercise requirements

Create a RESTful MicroService which will continuously calculate the average time it takes to
perform a named task.

There are N tasks that need to be tracked, each with a unique identifier.
You are asked to implement two methods:

- Task performed: It accepts the task identifier and the duration in milliseconds. It
  performs the necessary calculations, data storing and it returns ok or an error.
- Current average time: It accepts the task identifier and it returns the task identifier
  and the average duration in milliseconds.

Write the service in Java and design it in such a way that it survives restarts. If the service is
restarted the recorded averages should still be available once the service is back up and
running.

Approach this problem as if you are writing a production ready service, keeping in mind non-functional
requirements such as test coverage, scalability, availability, performances, etc.

You can engineer the solution to show appropriate design patterns and design choices.
Optional: Package up the service in docker and run it docker-compose (or similar)


## Dockerized
To build the docker image for the app run the following from the root of the project:
```shell
mvn spring-boot:build-image
```