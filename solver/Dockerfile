# -------------------------------
# Stage 1: Build the jar
# -------------------------------
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only the pom.xml first for caching
COPY pom.xml .

# Copy the source code
COPY src ./src

# Build the jar (skip tests for faster build)
RUN mvn clean package -DskipTests

# -------------------------------
# Stage 2: Run the app
# -------------------------------
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/solver-0.0.1-SNAPSHOT.jar app.jar

# Expose default Spring Boot port (optional)
EXPOSE 8080

# Use Render's dynamic port
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --server.port=$PORT"]
