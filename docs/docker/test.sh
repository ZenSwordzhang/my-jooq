#!/usr/bin/bash

createRedisConfig() {
  echo "Start writing to redis configuration file"
  for port in $(seq 7000 7005); do
    if [ ! -d "d:/usr/local/etc/docker/redis-cluster/config/${port}" ]; then
      mkdir -p d:/usr/local/etc/docker/redis-cluster/config/"${port}"
    fi
    # 使用"<<-"，需要使用制表符Tab缩进
    # 使用<<-'EOF'或<<'EOF'，其中的内容都不能进行变量替换，使用<<EOF可以将外部变量传递到内容中
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
createRedisConfig