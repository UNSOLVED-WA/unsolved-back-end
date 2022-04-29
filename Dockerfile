FROM openjdk:11-jre-slim AS build
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:11-jre-slim
COPY --from=builder build/libs/*.jar app.jar

ARG ENVIRONMENT
ENV SPRINg_PROFILES_ACTIVE=${ENVIRONMENT}

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

