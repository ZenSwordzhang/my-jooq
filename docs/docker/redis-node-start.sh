#!/bin/bash

# 方法要定义在调用的前面，否则会因找不到该方法报错
createRedisClusterContainer() {
  echo "Start to create redis cluster"
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
  echo "Successfully created redis cluster"
}

# 查看redis-net是否存在
network=$(docker network ls | grep redis-net)
if [ "$network" ]; then
  printf "Network named redis-net already exists：%s\n" "$network"
else
  echo "Start creating a network called redis-net"
  docker network create redis-net
  echo "Successfully created redis-net"
fi

container_names=
# 生成要检查的容器名
for port in $(seq 7000 7005); do
  container_names=$container_names"|redis-${port}"
done

#echo "container_names: $container_names"

# 去掉字符串首位的"|"，得到grep命令的匹配部分
grep_part="${container_names:1}"
#echo "grep_part: $grep_part"

## 拼接命令实现过滤，匹配容器信息，判断容器是否存在，如果容器不存在，直接创建
info_list=$(docker ps -a | grep -E "$grep_part" | tee redis.txt)
if test -z "$info_list"; then
  createRedisClusterContainer
  docker ps -a | grep -E "$grep_part"
  exit
fi
# 按2个以上空格进行全局替换
< redis.txt sed 's/[[:space:]]\{2,\}/=/g' > tmpfile && mv tmpfile redis-sed.txt
# 按"="好进行切分，获取匹配的容器名
container_names_list=$(cut -d "=" -f7 redis-sed.txt)
printf 'Redis cluster(%s)\n already exists, whether to rebuild after deletion? y/n:' "$container_names_list"
read -r deleted_str
if [ "$deleted_str" == "y" ]; then
  echo "Start to delete redis cluster"
  remove_redis_cluster="docker rm -f""${container_names//|/' '}"
  $remove_redis_cluster
  echo "Successfully deleted redis cluster"
  createRedisClusterContainer
  docker ps -a | grep -E "$grep_part"
fi


