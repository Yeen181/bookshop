# FROM eclipse-temurin:17-jdk-alpine as build
# WORKDIR ./src
# COPY . .
# # COPY .mvn .mvn
# # COPY pom.xml .
# # COPY src src
# RUN chmod 777 ./mvnw
# RUN --mount=type=cache,target=/root/.m2 mvnw.cmd install -DskipTests

# FROM eclipse-temurin:17-jdk-alpine
# COPY --from=build /src/target/bookstore-0.1.jar app.jar
# #COPY target/*.jar  app.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "app.jar"]

FROM maven:3-eclipse-temurin-17-alpine as BUILDER
LABEL authors = "Tuanpa"
WORKDIR ./src
COPY . .
RUN mvn -f pom.xml clean package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=BUILDER /src/target/bookstore-0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]