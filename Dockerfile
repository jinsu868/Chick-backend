FROM openjdk:17.0-slim as Build

COPY . /app

WORKDIR /app

RUN chmod +x gradlew

RUN ./gradlew build -x test

FROM openjdk:17.0-slim

#RUN gradle clean build -x test

ARG JAR_FILE=build/libs/*jar

COPY --from=Build /app/${JAR_FILE} app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]