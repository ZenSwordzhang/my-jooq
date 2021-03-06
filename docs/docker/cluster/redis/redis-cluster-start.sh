#!/bin/bash
##-----------------注：删除后重新创建集群，如果挂载数据的目录已存在数据，创建集群分配节点时会失败--------------

set -x
set -e

container_names=
# 声明一个单节点容器名，后续创建集群分配节点需要
redis_single_container_name=
net_name=shared
config_dir=/data/dmplus/facilities/config/redis-cluster
data_dir=/data/dmplus/facilities/data/redis-cluster

ports=$(seq 7000 7005)
host_ip="172.17.0.1"

# 方法要定义在调用的前面，否则会因找不到该方法报错
createRedisClusterContainer() {
  # 设置绿色字体echo -e "\033[32m 绿色字 \033[0m"
  echo -e "\033[32m Start to create redis cluster container \033[0m"
  # 注：此处redis.conf文件如果不存在，会默认创建名为redis.conf的文件夹
  for port in ${ports}; do
    docker run -d -it \
      -p "${port}":"${port}" \
      -p 1"${port}":1"${port}" \
      -v ${config_dir}/"${port}"/redis.conf:/usr/local/etc/redis/redis.conf \
      -v ${data_dir}/"${port}":/data \
      --name redis-"${port}" \
      --net ${net_name} \
      redis:buster \
      redis-server /usr/local/etc/redis/redis.conf || fail_exit
  done
  echo -e "\033[32m Successfully created redis cluster container \033[0m"
}

createRedisConfig() {
  echo -e "\033[32m Start writing to redis configuration file \033[0m"
  for port in ${ports}; do
    # 注：windows下使用盘符路径，ubuntu下使用绝对路径
    if [ ! -d "${config_dir}/${port}" ]; then
      mkdir -p ${config_dir}/"${port}"
    fi
    # 使用"<<-"，需要使用制表符Tab缩进
    # 使用<<-'EOF'或<<'EOF'，其中的内容都不能进行变量替换，使用<<EOF可以将外部变量传递到内容中
    # 注：此处若存在名为redis.conf的文件夹，则不再创建redis.conf文件
    sudo tee ${config_dir}/"${port}"/redis.conf <<EOF
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
  echo -e "\033[32m Writing redis configuration file is complete \033[0m"
}

createClusterDistributionNodes() {
  echo -e "\033[32m Start creating a cluster \033[0m"
  host_list=
  for port in ${ports}; do
    host_list="$host_list$host_ip:${port} "
  done
  # 模拟键盘输入，自动选择yes，yes 命令可以无限重复产生其后面的字符”yes”，head 命令选择重复输入的次数
  redis_cli_order="yes yes | head -1 | redis-cli --cluster create ${host_list} --cluster-replicas 1"
  echo "$redis_cli_order"
  # 在容器redis-7000外部执行执行容器内的命令，进行主从节点分配
  #  docker exec -it redis-7000 bash -c "$redis_cli_order"
  docker exec -it "$redis_single_container_name" bash -c "$redis_cli_order" || fail_exit
  echo -e "\033[32m Successfully created cluster \033[0m"
}

createRedisCluster() {
  createRedisConfig || fail_exit
  createRedisClusterContainer || fail_exit
  createClusterDistributionNodes || fail_exit
}

createNetWork() {
  # 查看网络shared是否存在
  local net_work
  net_work=$(docker network ls | grep ${net_name})
  if [ "$net_work" ]; then
    #  黄色字：printf "Network named ${net_name} already exists：%s\n" "$net_work"
    echo -e "\033[33m Network named ${net_name} already exists：\n $net_work \033[0m"
  else
    echo -e "\033[32m Start creating a network called ${net_name} \033[0m"
    docker network create ${net_name}
    echo -e "\033[32m Successfully created ${net_name} \033[0m"
  fi
}

fail_exit() {
  echo -e "\033[31m Failed to create redis cluster \033[0m"
  exit
}

generate_splicing_container_name() {
  # 生成要检查的容器名，并拼接"|"
  # 注意：此处的容器名createRedisClusterContainer()方法中生成的容器名要一致
  for port in ${ports}; do
    container_names=$container_names"|redis-${port}"
    if test -z "$redis_single_container_name"; then
      redis_single_container_name=redis-${port}
    fi
  done
}


createNetWork || fail_exit

generate_splicing_container_name

#echo "container_names: $container_names"

# 去掉字符串首位的"|"，得到grep命令的匹配部分
grep_part="${container_names:1}"
#echo "grep_part: $grep_part"

## 拼接命令实现过滤，匹配容器信息，判断容器是否存在，如果容器不存在，直接创建
info_list=$(docker ps -a | grep -E "$grep_part" | sudo tee redis.txt)
if test -z "$info_list"; then
  createRedisCluster
  docker ps -a | grep -E "$grep_part"
  exit
fi
# 按2个以上空格进行全局替换
#sudo sed <redis.txt 's/[[:space:]]\{2,\}/=/g'>tmpfile && sudo mv tmpfile redis-sed.txt
sudo cat redis.txt | sudo sed 's/[[:space:]]\{2,\}/=/g' | sudo tee redis-sed.txt > /dev/null
# 按"="好进行切分，获取匹配的容器名
container_names_list=$(sudo cut -d "=" -f7 redis-sed.txt)

echo ""
# 红色字：printf 'Redis cluster(%s)\n already exists, whether to rebuild after deletion? y/n:' "$container_names_list"
echo -e "\033[31mRedis cluster
($container_names_list})
already exists, whether to rebuild after deletion?
注：删除后重新创建集群，如果挂载数据的目录已存在数据，创建集群分配节点时会失败 y/n:  \033[0m"

read -r deleted_str
if [ "$deleted_str" == "y" ]; then
  echo -e "\033[32m Start to delete redis cluster \033[0m"
  remove_redis_cluster="docker rm -f""${container_names//|/' '}"
  $remove_redis_cluster
  echo -e "\033[32m Successfully deleted redis cluster \033[0m"
  createRedisCluster
  docker ps -a | grep -E "$grep_part"
fi
