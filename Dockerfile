# syntax=docker/dockerfile:1

# --- build stage ---
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Копируем только pom.xml, чтобы кэшировать загрузку зависимостей
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# Копируем исходники и собираем jar
COPY src ./src
RUN mvn -q -DskipTests package


# --- runtime stage ---
FROM eclipse-temurin:17-jre

WORKDIR /app

# Spring Boot по умолчанию слушает 8080
EXPOSE 8080

# Кладём собранный jar
COPY --from=builder /app/target/*SNAPSHOT.jar /app/app.jar

# На случай, если в будущем понадобятся параметры JVM, оставляем JAVA_OPTS
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
