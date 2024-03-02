FROM maven:3.8.5-openjdk-17
WORKDIR /app
COPY src src
COPY pom.xml pom.xml
RUN mvn clean package
RUN mv target/*.jar target/common.jar
