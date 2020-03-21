FROM redis:5.0.3-alpine3.9
MAINTAINER BruceHsu <bruce770405@gmail.com>
EXPOSE 6379 6379
COPY redis.conf /etc/redis/redis.conf
RUN redis-server /etc/redis/redis.conf