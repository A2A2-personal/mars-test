FROM openjdk:21-jdk-slim AS builder
WORKDIR /app
COPY target/mars-test-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
