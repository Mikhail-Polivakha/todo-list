FROM gradle:jdk11 AS build
WORKDIR /application
COPY . .
RUN gradle clean build

FROM openjdk:11.0.8-jre-slim
COPY --from=build /application/build/libs/*.jar ./application.jar

CMD java -jar ./application.jar