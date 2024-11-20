FROM maven:3.8.5-amazoncorretto-11 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM amazoncorretto:11
COPY --from=build /target/task_management-0.0.1-SANAPSHOT.jar task_management.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "task_management.jar"]