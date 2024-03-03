FROM alpine
WORKDIR /app
COPY target/*.jar common.jar

# CMD ["java", "-jar", "app.jar"]
