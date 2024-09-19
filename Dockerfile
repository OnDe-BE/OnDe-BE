FROM openjdk:17.0.1-jdk-slim AS builder
LABEL authors="minjuko"

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN ls
RUN chmod +x ./gradlew
RUN ./gradlew bootJar
RUN cd build/libs
RUN ls

FROM openjdk:17.0.1-jdk-slim
COPY --from=builder build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]