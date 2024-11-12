# Build the project using Maven
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml ./
COPY src ./src

# Build the project and package it as a JAR file
RUN mvn clean package -DskipTests

# Second stage: Create a lightweight image to run the application
FROM eclipse-temurin:17-jre

# Set the working directory
WORKDIR /app

# Set environment variables
ENV HTTP_PORT=2080 \
    TCP_PORT=2021 

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application's port (matches APP_PORT)
EXPOSE 2080 2021

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
