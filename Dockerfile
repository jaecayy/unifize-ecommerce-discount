FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY . .

RUN chmod +x mvnw

FROM build AS test
WORKDIR /app
RUN ./mvnw test


FROM eclipse-temurin:17-jre-alpine

WORKDIR /app


COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
