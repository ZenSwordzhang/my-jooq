#!/bin/bash

docker network create redis-net

for port in $(seq 7000 7005); do \
  docker run -d -it \
    -p "${port}":"${port}" \
    -p 1"${port}":1"${port}" \
    -v /d/usr/local/etc/docker/redis-cluster/config/"${port}"/redis.conf:/usr/local/etc/redis/redis.conf \
    -v /d/usr/data/docker/redis-cluster/"${port}":/data \
    --name redis-"${port}" \
    --net redis-net \
    --sysctl net.core.somaxconn=1024 \
    redis:buster \
    redis-server /usr/local/etc/redis/redis.conf; \
done
