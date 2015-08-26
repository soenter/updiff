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

RUN echo "export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64/" > $HOME/.bashrc

# install maven
RUN sudo apt-get install maven -y

# build updiff
RUN mvn clean package -DskipTests=true

# install updiff
RUN mv updiff-upper/target/updiff-upper-1.0.4-assembly.tar.gz $HOME/

RUN cd $HOME

RUN echo "export UPPER_HOME=$HOME/updiff-upper-1.0.4" > $HOME/.bashrc
RUN echo "export PATH=$UPPER_HOME/bin:$PATH" > $HOME/.bashrc

RUN source $HOME/.bashrc

# run updiff

RUN upper

