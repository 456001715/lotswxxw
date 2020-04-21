#VERSION 1.1.0
#基础镜像为openjdk:12-alpine

FROM openjdk:12-alpine

#签名
MAINTAINER lots "553294090@qq.com.com"


RUN rm -rf /opt/running/lotswxxw*
ADD ./target/bootshiro.jar /opt/running/lotswxxw.jar

EXPOSE 8080
WORKDIR /opt/running/

CMD ["java", "-jar", "lotswxxw.jar","--spring.profiles.active=prod"]