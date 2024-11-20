FROM maven:3.8.5-amazoncorretto-11 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM amazoncorretto:11
COPY --from=build /target/task-management-0.0.1-SANAPSHOT.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]