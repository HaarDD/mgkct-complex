FROM maven:3.8.7-openjdk-18-slim AS build
LABEL authors="Maxim Galitskiy"

ENV HOME=/app

RUN mkdir -p $HOME

WORKDIR $HOME

ADD pom.xml $HOME

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

ADD . $HOME

RUN ["mvn", "package"]

FROM openjdk:17-slim
LABEL authors="Maxim Galitskiy"

WORKDIR /app
COPY --from=build /app/target/cclog.jar .

CMD ["java", "-jar", "cclog.jar"]

