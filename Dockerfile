# Stage 1: Build the application
FROM eclipse-temurin:22-jdk AS build
WORKDIR /app
COPY blog-backend/pom.xml .
COPY blog-backend/src ./src
RUN mvn clean install -DskipTests


# Stage 2: Create a lightweight image for the application
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
