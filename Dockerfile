FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from:build /target/*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]