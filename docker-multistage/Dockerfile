ARG SRC_CODE_PATH=/usr/src/complete
ARG GIT_URL=https://github.com/spring-guides/gs-spring-boot.git
ARG BRANCH=master
ARG DEPTH=1
ARG MVN_GOAL="clean package"

FROM alpine/git as puller
ARG GIT_URL
ARG BRANCH
ARG DEPTH

###################
#### PULLER STAGE
###################
RUN mkdir /tmp/src && \
  git clone \
  -b $BRANCH \
  --depth $DEPTH \
  $GIT_URL \
  /tmp/src

###################
#### BUILD STAGE
###################
FROM maven:3.6 AS build  
ARG SRC_CODE_PATH
ARG MVN_GOAL
COPY --from=puller /tmp/src /usr/src/

RUN mvn -f $SRC_CODE_PATH/pom.xml \
  $MVN_GOAL && \
  mkdir /tmp/target && \
  cp $SRC_CODE_PATH/target/*.jar /tmp/target

###################
#### RUNTIME STAGE
###################
#https://hub.docker.com/r/fabric8/java-centos-openjdk11-jdk
FROM fabric8/java-centos-openjdk11-jdk:1.6.2 as runtime
COPY --from=build /tmp/target/*.jar /usr/app/app.jar  
EXPOSE 8080  
ENV JAVA_APP_DIR=/usr/app/
ENV JAVA_APP_JAR=app.jar

###################
#### RUNTIME ALPINE STAGE
###################
FROM ibmjava:jre-alpine as runtime-alpine
ARG SRC_CODE_PATH
COPY --from=build /tmp/target/*.jar /usr/app/app.jar  
EXPOSE 8080  
#ENTRYPOINT ["java","-jar","/usr/app/app.jar"]  
COPY ./docker-entrypoint.sh /
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["java","-jar","/usr/app/app.jar"]  

###################
#### DEBUG STAGE
###################
FROM runtime as debug
USER root
RUN yum update -y --skip-broken  && \
  yum install -y iputils && \
  yum clean all
#COPY --from=nginx:latest /etc/nginx/nginx.conf /nginx.conf

# Setting default stage
FROM runtime

