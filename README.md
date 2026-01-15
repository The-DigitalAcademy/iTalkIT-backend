# iTalkIT Backend

This project is a Spring Boot REST API for the iTalkIT social media application.

## Prerequisites

Before running this project, ensure you have the following installed:
- Java JDK 21 or higher
- Maven 3.8 or higher
- PostgreSQL 15 or higher

Check installations:
```bash
java -version
mvn -version
psql --version
```

## Installation

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/italkit.git
cd italkit/italkit
```

### 2. Set up PostgreSQL Database
```bash
# Login to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE italkit_db;

# Exit
\q
```

### 3. Configure Application Properties

Update `src/main/resources/application.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/italkit_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server Configuration
server.port=8080

# CORS Configuration
spring.web.cors.allowed-origins=http://localhost:4200
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
spring.web.cors.allow-credentials=true
```

## Running the Application

### Option 1: Using Maven
```bash
# Clean and install dependencies
mvn clean install

# Run the application
mvn spring-boot:run
```

### Option 2: Using Maven Wrapper (no Maven installation required)
```bash
# On Linux/Mac
./mvnw clean install
./mvnw spring-boot:run

# On Windows
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

### Option 3: Run as JAR
```bash
# Build JAR file
mvn clean package

# Run JAR
java -jar target/italkit-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## Verify Application is Running
```bash
# Check if server is up
curl http://localhost:8080/api/users

# Or open in browser
http://localhost:8080/api/users
```

## API Endpoints

### Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `POST /api/users/{followerId}/follow/{followingId}` - Follow user
- `DELETE /api/users/{followerId}/unfollow/{followingId}` - Unfollow user

### Posts
- `GET /api/posts` - Get all posts
- `GET /api/posts/{id}` - Get post by ID
- `GET /api/posts/user/{userId}` - Get posts by user
- `GET /api/posts/feed/{userId}` - Get user's feed
- `POST /api/posts/user/{userId}` - Create post
- `PUT /api/posts/{id}` - Update post
- `DELETE /api/posts/{id}` - Delete post
- `POST /api/posts/{id}/like` - Like post
- `POST /api/posts/{id}/unlike` - Unlike post

## Project Structure
