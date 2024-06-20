# BackEndHealHub
Description

BackEndHealHub is a Spring Boot application designed for managing healthcare services. It provides functionalities including managing appointments, doctors, clinics, and user authentication with JWT security.
Technologies

    Spring Boot 2.6.3
    Java 17
    Spring Data JPA
    Spring Security
    MySQL
    JWT for Authentication/Authorization
    Maven
    Lombok
    ModelMapper

# Features

    User registration and authentication
    Appointment scheduling and management
    Doctor and clinic profile management
    Email notifications
    CORS configuration for cross-origin resource sharing

# Prerequisites

Before you begin, ensure you have the following installed:

    Java 17
    Maven
    MySQL

Setup

Clone the project from GitHub:

bash

git clone https://github.com/abdiladifart/BackEndHealHub/BackEndHealHub.git
cd BackEndHealHub

Configuration

Edit the src/main/resources/application.properties file to set up your database and other environment-specific settings:

# properties

spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabase
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

# Running the application locally

To run the application, use the following Maven command:

bash

mvn spring-boot:run

The application will start running at http://localhost:8080.
Building the application

To build the application and generate a .jar file, use:

bash

mvn clean install

You can then run the jar file using:

bash

java -jar target/backendhealhub-0.0.1-SNAPSHOT.jar

Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Ensure to update tests as appropriate.
License

