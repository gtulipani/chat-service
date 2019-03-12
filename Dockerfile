FROM openjdk:8u151-jdk-alpine

MAINTAINER Gaston Tulipani <gtulipani@hotmail.com>

ARG JAR_FILE
ENV JAR_NAME=${JAR_FILE}
ENV JAVA_OPTS="-Xms128M -Xmx256M -XX:MaxMetaspaceSize=128M"

RUN addgroup -g 1000 chat-service && \
    adduser -S -u 1000 -g chat-service chat-service

USER chat-service

COPY ./target/$JAR_NAME /opt/chat-service/
WORKDIR /opt/chat-service
EXPOSE 8080

CMD ["java", "-jar", "chat-service.jar", "-Xms128M -Xmx256M -XX:MaxMetaspaceSize=128M"]
