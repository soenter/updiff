# updiff
#
# VERSION       1.0.4

# use the ubuntu base image provided by dotCloud
FROM ubuntu

MAINTAINER Victor Coisne victor.coisne@dotcloud.com

# make sure the package repository is up to date
# RUN echo "http://mirrors.163.com/ubuntu/ precise main restricted universe multiverse" > /etc/apt/sources.list
RUN apt-get update

# install maven
RUN sudo apt-get install maven -y whatever

# build updiff
RUN mvn clean package -DskipTests=true

# run updiff

RUN ./bin/upper

