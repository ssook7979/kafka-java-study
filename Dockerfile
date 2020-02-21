FROM ubuntu:latest
ARG KAFKA_VERSION=kafka_2.12-2.4.0
ARG KAFKA_FILE_LINK=http://mirror.navercorp.com/apache/kafka/2.4.0/kafka_2.12-2.4.0.tgz
ENV KAFKA_VERSION=${KAFKA_VERSION}
WORKDIR /opt
RUN apt update -y && apt upgrade -y && apt install wget default-jdk maven -y && \
    wget ${KAFKA_FILE_LINK} && \
    tar -xzf ${KAFKA_VERSION}.tgz
CMD ${KAFKA_VERSION}/bin/zookeeper-server-start.sh ${KAFKA_VERSION}/config/zookeeper.properties