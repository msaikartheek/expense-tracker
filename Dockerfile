FROM openjdk:21-ea-11-jdk-slim
EXPOSE 8080
ARG JAR_FILE=target/tracker-0.0.1.jar
ADD ${JAR_FILE} tracker-0.0.1.jar
ENTRYPOINT ["java","--enable-preview"jenki,"-jar","/tracker-0.0.1.jar"]