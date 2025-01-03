# 1단계: Gradle 빌드
FROM gradle:7.6-jdk17 AS build

WORKDIR /app

# 프로젝트 소스 복사
COPY time-service ./time-service

# Gradle Wrapper 사용하여 빌드
WORKDIR /app/time-service
RUN chmod +x ./gradlew && ./gradlew clean build -x test --no-daemon

# 2단계: 애플리케이션 실행
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=build /app/time-service/build/libs/*.jar app.jar

EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
