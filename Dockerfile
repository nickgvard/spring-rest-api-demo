FROM openjdk:11.0.7-jdk-slim

COPY build/libs/spring-rest-api-demo-0.0.1.jar /demo.jar

CMD ["java", "-jar", "/demo.jar"]