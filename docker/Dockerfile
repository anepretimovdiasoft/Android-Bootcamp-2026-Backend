FROM maven:3.9.12-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn clean package spring-boot:repackage -DskipTests

FROM eclipse-temurin:17 AS runtime
COPY --from=build /workspace/target/*.jar /opt/app/app.jar
CMD ["java", "-jar", "/opt/app/app.jar"]