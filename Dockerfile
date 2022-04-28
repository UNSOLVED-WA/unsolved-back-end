FROM openjdk:11-jdk-alpine as builder
WORKDIR application
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} appliccation.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:11-jdk-alpine
WORKDIR application
ENV port 8080
ENV spring.profiles.active local
COPY --from=builder applitcation/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/applitcation/ ./

ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]
