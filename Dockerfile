# Runtime image only
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy pre-built jar into container
COPY build/libs/*.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]