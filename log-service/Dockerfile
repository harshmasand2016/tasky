#Use Official Java Image
FROM openjdk:17-jdk-slim
#Set Working Directory
WORKDIR /app
#Copy the fat JAR from Gradle Build Output
COPY build/libs/log-service.jar app.jar
#Run the Application
ENTRYPOINT ["java", "-jar", "app.jar"]
#Author
LABEL authors="Harsh Masand"

