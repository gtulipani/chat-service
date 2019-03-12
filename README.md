# chat-service

## Description
Microservice that acts as a Backend Service for a Chat.

## Building and Running
### Build
#### Locally using maven
The standard way to build the application is with the following [Maven](https://maven.apache.org/) command in the root path:
```
mvn clean install -DskipTests
```

Take into account that the previous command doesn't include running the UTs. These can be by omitting the `-DskipTests`
parameter or by executing the following command:
```
mvn test
```

After executing the tests, a HTML report is generated using surefire plugin under path:
```
target/surefire-reports/emailable-report.html
```

#### Locally using Dockerfile
There is an alternative way to build the application using a [Dockerfile](https://docs.docker.com/engine/reference/builder/).
The following command must be executed in the root path:
```
mvn clean package dockerfile:build
```

### Run
#### Locally using maven
The standard way to run the application is with the following maven command:
```
mvn spring-boot:run
```

Take into account that a MySQL Service running is required before executing that command.

#### Locally using Docker
There is an alternative way to run the application with [Docker](https://docker.io/). It provides an isolated
environment where each service can be started on different containers. The repo includes a [docker-compose](https://docs.docker.com/compose/)
file, which includes both the Application (exposed in port `8080`) and the MySQL Database (`33060`). The following
command must be executed in the root path:
```
docker-compose up
```
