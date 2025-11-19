# -------------------------------
# Stage 1: Build the jar
# -------------------------------
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy Maven files first for caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the jar
RUN mvn clean package -DskipTests

# -------------------------------
# Stage 2: Run the app
# -------------------------------
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/solver-0.0.1-SNAPSHOT.jar app.jar

# Expose default Spring Boot port (optional for Render)
EXPOSE 8080

# Use Render's dynamic PORT environment variable
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --server.port=$PORT"]
