# Linux image with maven
FROM maven:3.6.0-jdk-13
MAINTAINER Faizul Islam (faizulcse@gmail.com)

WORKDIR /project
USER root
COPY src /project/src
COPY pom.xml /project