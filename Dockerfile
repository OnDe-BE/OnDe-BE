FROM openjdk:17.0.1-jdk-slim AS builder
LABEL authors="minjuko"

# 필요한 파일들만 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
COPY src/main/resources/application.yml .

# gradle 실행 권한 부여 및 빌드
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:17.0.1-jdk-slim

# 빌드 결과물을 실행 컨테이너에 복사
COPY --from=builder build/libs/*.jar app.jar
COPY --from=builder src/main/resources/application.yml .

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
