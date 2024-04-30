# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file from your target directory to the container
COPY ./target/BackEndHealHub-0.0.1-SNAPSHOT.jar /app/myapp.jar

# Make port 8080 available to the world outside this container
EXPOSE 3360

# Run the jar file
CMD ["java", "-jar", "myapp.jar"]
