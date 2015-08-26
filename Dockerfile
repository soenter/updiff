# updiff
#
# VERSION       1.0.4

# use the ubuntu base image provided by dotCloud
FROM ubuntu

MAINTAINER Victor Coisne victor.coisne@dotcloud.com

# make sure the package repository is up to date
# RUN echo "http://mirrors.163.com/ubuntu/ precise main restricted universe multiverse" > /etc/apt/sources.list

RUN apt-get update

# install java
RUN sudo apt-get install openjdk-7-jdk -y

ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64
ENV PATH $PATH:$JAVA_HOME/bin

# install maven
RUN sudo apt-get install maven -y

# install curl
RUN sudo apt-get install curl -y

# build updiff

RUN curl -sf -o $HOME/updiff-1.0.4.tar.gz -L https://github.com/soenter/updiff/archive/v1.0.4.tar.gz

RUN tar -zxvf $HOME/updiff-1.0.4.tar.gz

RUN mvn -f $HOME/updiff-1.0.4/pom.xml clean package -DskipTests=true

# install upper
RUN mv $HOME/updiff-1.0.4/updiff-upper/target/updiff-upper-1.0.4-assembly.tar.gz $HOME/

RUN tar -zxvf $HOME/updiff-upper-1.0.4-assembly.tar.gz

ENV UPPER_HOME $HOME/updiff-upper-1.0.4
ENV PATH $UPPER_HOME/bin:$PATH

# run updiff

# Set default container command
ENTRYPOINT $UPPER_HOME/bin/upper

