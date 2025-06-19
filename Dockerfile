FROM openjdk:23-jdk-slim
WORKDIR /app
COPY target/Portfolio-1.0-SNAPSHOT.jar Portfolio-1.0-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "Portfolio-1.0-SNAPSHOT.jar"]
