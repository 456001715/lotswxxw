#VERSION 1.1.0
#基础镜像为primetoninc/jdk:1.8

FROM registry.cn-hangzhou.aliyuncs.com/lots/jdk:8

#签名
MAINTAINER lots "553294090@qq.com.com"


RUN rm -rf lots*
ADD lots.jar lots.jar

CMD ["java","-Dfile.encoding=utf-8", "-jar","-Duser.timezone=GMT+8", "lots.jar","--spring.profiles.active=prod",">/home/java/package/lots/lots.log 2>&1 &"]