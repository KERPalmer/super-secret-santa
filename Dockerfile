#Use an official OpenJDK image as the base
FROM openjdk:21-jdk-slim

#Set the working directory inside the container
WORKDIR /app

#Copy the built JAR file from the host to the container
COPY build/libs/super-secret-santa-0.0.1-SNAPSHOT.jar app.jar

#Expose port 8080 so the app can be accessed
EXPOSE 8080

#Define the command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
