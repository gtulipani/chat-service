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
