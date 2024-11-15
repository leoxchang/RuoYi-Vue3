FROM nginx:latest
VOLUME /tmp
ENV LANG en_US.UTF-8

#设置时区
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' >/etc/timezone
RUN mkdir /etc/nginx/logs
ADD ./dist/ /usr/share/nginx/html/

#将所需文件放到容器中
COPY ./docker/nginx.conf /etc/nginx/nginx.conf


EXPOSE 80
EXPOSE 443

CMD  nginx -g 'daemon off;'
