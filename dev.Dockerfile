FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app
COPY src src
COPY pom.xml pom.xml
RUN mvn clean package -DskipTests
RUN cp target/*.jar /app/common.jar

FROM alpine
WORKDIR /app
COPY --from=build /app/common.jar /app/common.jar
