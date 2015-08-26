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

# install wget
RUN sudo apt-get install wget -y

# install unzip
RUN sudo apt-get install unzip -y

# build updiff
RUN cd $HOME

#RUN curl -sf -o $HOME/updiff-1.0.4.zip -L https://github.com/soenter/updiff/archive/v1.0.4.zip

RUN wget https://github.com/soenter/updiff/archive/v1.0.4.zip

RUN unzip v1.0.4.zip

RUN cd $HOME/updiff-1.0.4

RUN mvn clean package -DskipTests=true

# install upper
RUN mv updiff-upper/target/updiff-upper-1.0.4-assembly.tar.gz $HOME/

RUN cd $HOME

RUN tar -zxvf updiff-upper-1.0.4-assembly.tar.gz

ENV UPPER_HOME $HOME/updiff-upper-1.0.4
ENV PATH $UPPER_HOME/bin:$PATH

# run updiff

# Set default container command
ENTRYPOINT $UPPER_HOME/bin/upper

