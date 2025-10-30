# ===== Stage 1: Build JAR =====
# Use Java 21 for stability (even though your pom targets 25)
# Spring Boot 3.5.x works fine with Java 21 runtime
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy Maven files first for dependency caching
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy the rest of your source code
COPY src ./src

# Package application (skip tests for faster build)
RUN mvn -B clean package -DskipTests

# ===== Stage 2: Run JAR =====
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy built JAR from build stage
COPY --from=builder /app/target/*.jar app.jar

# Dynamic port (important for Koyeb)
ENV PORT=8080
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
