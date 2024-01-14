# Первый этап: сборка приложения с использованием образа Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

# Второй этап: запуск приложения с использованием образа OpenJDK
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/shopmaven-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]