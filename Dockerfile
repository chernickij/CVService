FROM maven:3.8.3-openjdk-17 as builder
WORKDIR /opt/app
COPY . /opt/app/.
RUN mvn -f /opt/app/pom.xml clean install

FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]
