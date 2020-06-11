#!/bin/bash

# 方法要定义在调用的前面，否则会因找不到该方法报错
createRedisClusterContainer() {
  echo "Start to create redis cluster"
  # 注：此处redis.conf文件如果不存在，会默认创建名为redis.conf的文件夹
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

createRedisConfig() {
  echo "Start writing to redis configuration file"
  for port in $(seq 7000 7005); do
    # 注：windows下使用盘符路径，ubuntu下使用绝对路径
    if [ ! -d "d:/usr/local/etc/docker/redis-cluster/config/${port}" ]; then
      mkdir -p d:/usr/local/etc/docker/redis-cluster/config/"${port}"
    fi
    # 使用"<<-"，需要使用制表符Tab缩进
    # 使用<<-'EOF'或<<'EOF'，其中的内容都不能进行变量替换，使用<<EOF可以将外部变量传递到内容中
    # 注：此处若存在名为redis.conf的文件夹，则不再创建redis.conf文件
    tee d:/usr/local/etc/docker/redis-cluster/config/"${port}"/redis.conf <<EOF
# redis后台运行
daemonize no
# redis运行的端口号
port $port
# 指定只接收来自该ip地址的请求
bind 0.0.0.0
# 启动集群模式
cluster-enabled yes
# 集群配置文件，在集群启动时，自动创建
cluster-config-file nodes-$port.conf
# 节点总线端口
cluster-announce-bus-port 1$port
# 集群超时时间，节点超时多久表示宕机了
cluster-node-timeout 50000
# 开启aof持久化模式，每次写操作请求都追加到appendonly.aof文件中
appendonly yes
EOF
  done
  echo "Writing redis configuration file is complete"
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
  createRedisConfig
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
  createRedisConfig
  createRedisClusterContainer
  docker ps -a | grep -E "$grep_part"
fi


