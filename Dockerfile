# Stage 1: Build the JAR
FROM gradle:8.4-jdk17-alpine AS builder
WORKDIR /app

COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Show permissions + contents
RUN chmod +x gradlew && ls -l gradlew && ./gradlew --version

RUN ./gradlew bootJar --no-daemon

# Stage 2: Run the JAR
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]