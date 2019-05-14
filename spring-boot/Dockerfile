FROM maven:3.6 AS build  

COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
COPY settings.xml /usr/share/maven/ref/

RUN mvn -f /usr/src/app/pom.xml -s /usr/share/maven/ref/settings.xml clean package

FROM fabric8/java-centos-openjdk8-jre:1.6.2 as runtime

COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar  
EXPOSE 8080  
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]  

FROM ibmjava:jre-alpine as runtime-alpine
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar  
EXPOSE 8080  
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]  

FROM runtime as debug
USER root
RUN yum update -y --skip-broken  && \
  yum install -y iputils && \
  yum clean all
#COPY --from=nginx:latest /etc/nginx/nginx.conf /nginx.conf

FROM runtime