FROM gradle:8.3-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM openjdk:11-jre
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/user-management-service-1.0.0.jar /app/user-management-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/user-management-service.jar"]
