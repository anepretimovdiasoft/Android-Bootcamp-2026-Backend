FROM gradle:8.12-jdk21 AS builder

WORKDIR /app

COPY gradlew .
COPY gradle ./gradle

COPY build.gradle.kts settings.gradle.kts ./

COPY src ./src

RUN ./gradlew bootJar --no-daemon --quiet

FROM eclipse-temurin:21-jre-jammy

RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
