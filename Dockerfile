# Stage 1: Build the application using Maven image
FROM maven:3.8.5-eclipse-temurin-22 AS build
WORKDIR /app

# Copy the project files
COPY blog-backend/pom.xml .
COPY blog-backend/src ./src

# Build the project
RUN mvn clean install -DskipTests


# Stage 2: Create a lightweight image for the application
FROM openjdk:11-jre-slim
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
