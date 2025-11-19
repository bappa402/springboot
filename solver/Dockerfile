# ----------------------------
# 1. BUILD STAGE
# ----------------------------
FROM maven:3.9.5-eclipse-temurin-17 AS build

WORKDIR /app

# copy pom first to cache dependencies
COPY pom.xml .
RUN mvn -q -e -B dependency:go-offline

# copy project source
COPY src ./src

# build jar
RUN mvn -q -e -B clean package -DskipTests


# ----------------------------
# 2. RUNTIME STAGE
# ----------------------------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar


# RUN THE APP
ENTRYPOINT ["sh", "-c", "java -jar /app.jar --server.port=$PORT"]
EXPOSE 8080

