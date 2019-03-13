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

#### Locally using Kubernetes
There is another alternative way to run the application with [Kubernetes](https://kubernetes.io/). It provides another
abstraction layer from Docker or any other container-oriented application. The repo includes 3 different manifests:
- **mysql-volume.yaml**: [PersistentVolume](https://kubernetes.io/docs/concepts/storage/persistent-volumes/) for the
MySQL Database consumed by the service.
- **mysql.yaml**: [Service](https://kubernetes.io/docs/concepts/services-networking/service/) and 
[Deployment](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/) from the MySQL DB. Also a 
[Secrets](https://kubernetes.io/docs/concepts/configuration/secret/) is included to handle MySQL credentials.
- **chat-service.yaml**: Service and Deployment for the chat-service. It's configured to support 2
replicas and exposes the port `30161`. Also both [readinessProbe and livenessProbe](https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-probes/)
have been configured using the `/health` endpoint.

The manifests must be executed in the same order as listed above to avoid issues. Each one of them must be started with
the [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/) command:
```
kubectl apply -f mysql-volume.yaml
kubectl apply -f mysql.yaml
kubectl apply -f chat-service.yaml
```
The services status can be easily verified using the [Kubernetes Dashboard](https://github.com/kubernetes/dashboard). The
result should be similar to: ![](https://i.imgur.com/UOo5XYY.png)

## Technologies
### Spring
The Application is a standard [Spring Boot](https://spring.io/) Application and it's conformed by the following layers:
- **AuthenticationTokenInterceptor**: [HandlerInterceptor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/handler/HandlerInterceptorAdapter.html) 
to verify authentication for the requests
- **Controller**: handles all the incoming requests from the outside world. There are three of them: *MessageController*,
*UserController* and *LoginController*.
- **Service**: handles all the logic from the incoming requests from the controllers. There are also three of them: *MessageService*,
*UserService* and *LoginService*.
- **Repository**: [CrudRespository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)
that handles the last communication between the services and the Database. There are two of them: *MessageRepository* and
*UserRepository*.

### MySQL
The chosen Database Engine is [MySQL](https://www.mysql.com/), a free relational DB model. By the moment it contains
only two table called `users` and `messages`. The first one contains the following columns:
```
id|created_on|last_modified|usernane|password|token|
```

The second one contains the following columns:
```
id|created_on|last_modified|sender|recipient|content|
```

### Flyway
[Flyway](http://flywaydb.org/) is a database migration tool similar to Liquibase. It is recommended by Spring Boot.
See the documentation. The migrations scripts are in SQL directly.

The project is configured to run the migration scripts on start.

### Bearer Token Authentication
The `/messages` endpoint is authenticated with [Bearer Token](https://oauth.net/2/bearer-tokens/) Authorization. The token
is provided as part of the response of the `/login` API and it doesn't expire at the moment. If no token is found as part
of these requests, or an incorrect one is found instead, a **401** (Unauthorized) Error Message is returned.

### Orika Mapping
[Orika](https://orika-mapper.github.io/orika-docs/) is a framework used to convert different entities on a extensible, clear
and practical way. We have defined a *CustomMapperStrategy* that acts as a [CustomMapper](https://orika-mapper.github.io/orika-docs/advanced-mappings.html)
among with a `Strategy` pattern, in order to be able to register them programatically (take a look at `MessageMapper`).
The mappers reduce the work necessary to convert between the necessary entities. E.g. to convert between a entity of type
*MessageCreationRequest* to an entity of type *Message*, we just need to:
```
messageMapper.map(messageCreationRequest, Message.class)
``` 

### AES Encryption Service
[Advanced Encryption Standard](https://en.wikipedia.org/wiki/Advanced_Encryption_Standard) is a subset of Rijndael block
cypher with many intermediate steps used to encrypt data using an *encryption key* and an *initialization vector*. These
two values are included in the `application.yaml`, but could be easily overriden on each environment, so that the data
remains secured on each particular environment. A Service has been created that implements this algorithm, in order to 
securely store all sensitive data (`password` and `token`).

### Sensitive Field Converter
A [JPA AttributeConverter](https://www.baeldung.com/jpa-attribute-converters) has been created in order automatically:
- Encrypt all sensitive fields before storing them in the DB
- Decrypt all sensitive fields after retrieving them from the DB

## Postman Collection
A [Postman Collection](https://www.getpostman.com/) has been included in the repository, containing all the existing endpoints
among with examples:
### GET /messages
API to get all the messages for a given recipient starting with a given messageId (or the next one lower to it). It
accepts three query parameters: `recipient`, `start` and `limit`. The last parameter is optional and its default value
is 100. The response includes all the messages with the recipient passed as parameter starting from the messageId defined
with the `start` parameter:
```
{
    "messages": [
        {
            "id": 1,
            "timestamp": "2019-03-12T02:52:52.000+0000",
            "sender": 1,
            "recipient": 2,
            "content": {
                "type": "text",
                "text": "Example message"
            }
        }
    ]
}
```

### POST /messages
API to create a new message. The id from the message among with the timestamp is returned as part of the response. Expected input payload:
```
{
    "sender": 1,
    "recipient": 2,
    "content": {
        ...
    }
}
```

Example response payload:
```
{
    "id": 1,
    "timestamp": "2019-03-12T02:55:38.187+0000"
}
```

Three different type of messages can be created: *text*, *image* or *video*, each one of them with different content format.

#### Text Message
The `content` contains only a `text` field:

```
"content": {
    "type": "text",
    "text": "Example message"
}
```

#### Image Message
The `content` contains `url`, `height` and `width` fields:

```
"content": {
    "type": "image",
    "url": "https://media.licdn.com/dms/image/C4E0BAQF768b2Tj5qQQ/company-logo_200_200/0?e=2159024400&v=beta&t=BiHrNL31_Jj1o2-tRifxoFXWeYhkfM-Xu4mcCPkQ6j0",
    "height": 200,
    "width": 200
}
```

#### Video Message
The `content` contains `url` and `source` fields:

```
"content": {
    "type": "video",
    "url": "https://www.youtube.com/watch?v=X3paOmcrTjQ",
    "source": "youtube"
}
```

### POST /users
API to create a new user with `username` and `password` in the system. The `id` from the user is returned as part of the payload.
Example input payload:
```
{
    "username": "gtulipani",
    "password": "password"
}
```

Example response payload:
```
{
    "id": "1"
}
```

### POST /login
API to login with an existing username and password. A token is returned as part of the payload, and can be user for the
subsequent authenticated APIs. Example input payload:
```
{
    "username": "gtulipani",
    "password": "password"
}
```

Example response payload:
```
{
    "id": 1,
    "token": "Z19uvknyEbFmr3s7qkuPj"
}
```

## CI
[Travis CI](https://travis-ci.org/) has been chosen as CI Software. It's already configured and runs all the UT for
every PR and Build (including `master`). The status can be found at the top of this file.
