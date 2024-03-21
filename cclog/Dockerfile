FROM maven:3.8.7-openjdk-18-slim AS build
LABEL authors="Maxim Galitskiy"

ARG profile=""

ENV HOME=/app
ENV JAVA_TOOL_OPTIONS="-Dspring.profiles.active=${profile}"

RUN mkdir -p $HOME

WORKDIR $HOME

ADD pom.xml $HOME

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "-P${profile}", "clean", "--fail-never"]

ADD . $HOME

RUN ["mvn", "package"]

COPY frontend ./frontend

FROM openjdk:17-slim
LABEL authors="Maxim Galitskiy"

ARG profile=""
ENV JAVA_TOOL_OPTIONS="-Dspring.profiles.active=${profile}"

WORKDIR /app
COPY --from=build /app/target/cclog.jar .

CMD ["java", "-jar", "cclog.jar"]
