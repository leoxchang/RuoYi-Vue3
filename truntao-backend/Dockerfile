FROM  mcr.microsoft.com/java/jdk:17u1-zulu-centos

MAINTAINER truntao

#可在build时通过--build-arg参数传入（docker build --build-arg TIME_ZONE=Asia/Shanghai Dockerfile）
#List of tz database time zones
#https://en.wikipedia.org/wiki/List_of_tz_database_time_zones
ARG TIME_ZONE=Asia/Shanghai
#增加中文支持
ENV LANG=C.utf-8
ENV JAVA_OPTS=""
#添加fontconfig包支持java font操作
RUN sed -i -e "s|mirrorlist=|#mirrorlist=|g" /etc/yum.repos.d/CentOS-* \
    && sed -i -e "s|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g" /etc/yum.repos.d/CentOS-*
RUN rm -f /etc/yum.repos.d/zulu*
RUN yum install -y fontconfig && fc-cache --force


#设置时区&&给执行文件增加可执行权限
RUN /bin/cp /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime \
    && echo ${TIME_ZONE} >/etc/timezone

RUN mkdir -p /microservice

WORKDIR /microservice
ADD ./truntao-admin/target/truntao-admin.jar ./

CMD ["/bin/sh", "-c", "java $JAVA_OPTS -jar truntao-admin.jar"]
