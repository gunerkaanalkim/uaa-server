FROM maven:3.8.5-openjdk-17 as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests=true

FROM amazoncorretto:17-alpine-jdk as prod
RUN mkdir /app
COPY --from=builder /app/target/*.jar /app/auth-server.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","/app/auth-server.jar"]