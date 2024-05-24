FROM nginx:1.18

VOLUME /tmp
ENV LANG en_US.UTF-8

#设置时区
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' >/etc/timezone

ADD ./dist/ /usr/share/nginx/html/

#将所需文件放到容器中
COPY conf/nginx.conf /etc/nginx/nginx.conf
#自定义的配置
COPY conf/customer.conf /etc/nginx/conf.d/default.conf

CMD ["nginx", "-g", "daemon off;"]

