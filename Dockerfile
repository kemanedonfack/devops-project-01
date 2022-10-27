FROM maven:3.8.6-openjdk-18
ARG JAR_FILE = target/zencaisse-2.0.1.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/app.jar"]
