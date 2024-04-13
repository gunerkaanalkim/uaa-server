FROM maven:3.8.5-openjdk-17 as builder
WORKDIR /app
COPY pom.xml .
RUN mkdir -p /root/.m2 \
    && mkdir /root/.m2/repository
COPY docker/settings.xml /root/.m2
COPY src ./src
RUN mvn clean install

FROM amazoncorretto:17-alpine-jdk as prod
RUN mkdir /app
COPY --from=builder /app/target/*.jar /app/auth-server.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","/app/auth-server.jar"]