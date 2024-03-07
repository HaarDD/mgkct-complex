FROM maven:3.8.7-openjdk-18-slim AS build
LABEL authors="Maxim Galitskiy"

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -Dskiptests


FROM openjdk:17-slim
LABEL authors="Maxim Galitskiy"

WORKDIR /app
COPY --from=build /app/target/cclog.jar .

CMD ["java", "-jar", "cclog.jar"]

