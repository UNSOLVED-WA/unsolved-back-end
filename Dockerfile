FROM openjdk:11-jdk-slim AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:11-jdk-slim
COPY --from=builder build/libs/*.jar app.jar
COPY src/main/resources/application.yaml .

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

