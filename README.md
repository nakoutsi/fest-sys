# Festival Management System

This project is a Spring Boot application for managing festivals and performances. It exposes a REST API that allows you to register users, create festivals and submit performances.

## Prerequisites

- **Java 21** – make sure `java -version` prints Java 21.
- **Maven 3** – the project uses the Maven wrapper (`./mvnw`) so no global installation is required, but Maven must be available to run the wrapper script.
- **MySQL** – a running MySQL server is required for persistence.

## Configuring `application.properties`

The database connection and server port are configured in `src/main/resources/application.properties`:

```properties
spring.application.name=festival-management-system
spring.datasource.url=jdbc:mysql://localhost:3306/festivaldb?allowPublicKeyRetrieval=true&useSSL=true
spring.datasource.username=user
spring.datasource.password=StrongPass123!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

server.port=8080
```

Edit the URL, username and password values to match your local MySQL setup. The database `festivaldb` must exist and the specified user must have access to it.

## Building and Running

From the project root run:

```bash
./mvnw clean package       # Build the application
./mvnw spring-boot:run     # Start the Spring Boot server
```

Alternatively, after packaging you can run the jar directly:

```bash
java -jar target/festival-management-system-0.0.1-SNAPSHOT.jar
```

The application starts on port 8080 by default.

## Example Requests

### Register and Login

```bash
# Register a new user
curl -X POST http://localhost:8080/api/users/register \
     -H "Content-Type: application/json" \
     -d '{"username":"alice","password":"secret","fullName":"Alice"}'

# Authenticate
curl -X POST http://localhost:8080/api/users/login \
     -H "Content-Type: application/json" \
     -d '{"username":"alice","password":"secret"}'
```

### Creating a Festival

```bash
AUTH=$(echo -n 'alice:secret' | base64)

curl -X POST http://localhost:8080/api/festivals \
     -H "Authorization: Basic $AUTH" \
     -H "Content-Type: application/json" \
     -d '{
           "name":"My Fest",
           "description":"Annual music festival",
           "startDate":"2024-06-01",
           "endDate":"2024-06-02",
           "venue":"Central Park"
         }'
```

### Searching Festivals

```bash
curl -X GET "http://localhost:8080/api/festivals/search?name=My" \
     -H "Authorization: Basic $AUTH"
```

### Submitting a Performance

```bash
curl -X POST http://localhost:8080/api/performances/{festivalId} \
     -H "Authorization: Basic $AUTH" \
     -H "Content-Type: application/json" \
     -d '{"name":"Band Performance","description":"Rock set","genre":"Rock","duration":60}'
```

Replace `{festivalId}` with the id of an existing festival.

More endpoints can be found in the controller classes in `src/main/java/com/second/festivalmanagementsystem/controller`.
