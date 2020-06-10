#!/bin/bash

# 查看redis-net是否存在
network=$(docker network ls | grep redis-net)
if [ "$network" ]; then
  printf "redis-net已存在：%s\n" "$network"
else
  docker network create redis-net
fi

# shell声明一个数组
declare -a container_names

# 生成要检查的容器名

for port in $(seq 7000 7005);
do
  container_names=("${container_names[*]}" redis-"${port}")
done


# 查看container是否已存在
docker ps -a | grep

for port in $(seq 7000 7005);
do
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
