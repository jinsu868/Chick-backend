FROM openjdk:17-jdk-slim as Build

COPY . /app

WORKDIR /app

RUN chmod +x gradlew

RUN ./gradlew build -x test

FROM openjdk:17-jdk-slim

ARG JAR_FILE=build/libs/cheek-0.0.1-SNAPSHOT.jar
COPY --from=Build /app/${JAR_FILE} app.jar

EXPOSE 8080