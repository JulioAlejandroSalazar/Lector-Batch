# stage 1: build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# copiar pom y código fuente
COPY pom.xml .
COPY src ./src

# compilar y empaquetar sin tests
RUN mvn clean package -DskipTests

# stage 2: runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# copiar el jar compilado desde el build
COPY --from=build /app/target/*.jar lector_batch.jar

# copiar los archivos CSV si los tienes localmente
COPY data/ ./data/

# opcional: exponer puerto si necesitas debug (no necesario para batch)
# EXPOSE 8080

# comando para ejecutar el batch
ENTRYPOINT ["java", "-jar", "lector_batch.jar"]
