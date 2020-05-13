## 问题

### 问题1：this node is not a swarm manager. Use "docker swarm init" or "docker swarm join" to connect this node to swarm and try again
* 背景：使用docker-compose命令启动kafka集群报错
* 解决：docker swarm init

## kafka-manager界面
### 拉取镜像
* docker pull solsson/kafka-manager

### 启动容器
* docker run -d -p 9000:9000 --name kafka-manager --restart always -eZK_HOSTS=192.168.1.110 solsson/kafka-manager

### 进入容器
* docker exec -it kafka-manager bash

### 查找配置文件在容器中的位置
find / -name 'application.conf'

### 切换到配置文件所在目录
* cd conf

### 修改配置文件
* vim application.conf

### 修改配置
* kafka-manager.zkhosts="zoo1:2181,zoo2:2182,zoo3:2183"

### 退出容器
* exit

### 重启重启
* docker restart kafka-manager

## zookeeper集群

### 拉取zookeeper镜像
* docker pull zookeeper

### 编写启动配置文件：docker-compose-zookeeper.yml
```docker-compose-zookeeper.yml
version: '3.1'
 
services:
  zoo1:
    image: zookeeper
    restart: always
    hostname: zoo1
    ports:
      - 2181:2181
    environment:
      ZOO_MY_ID: 1
      ZOO_SERVERS: server.1=0.0.0.0:2888:3888;2181 server.2=zoo2:2888:3888;2181 server.3=zoo3:2888:3888;2181
 
  zoo2:
    image: zookeeper
    restart: always
    hostname: zoo2
    ports:
      - 2182:2181
    environment:
      ZOO_MY_ID: 2
      ZOO_SERVERS: server.1=zoo1:2888:3888;2181 server.2=0.0.0.0:2888:3888;2181 server.3=zoo3:2888:3888;2181
 
  zoo3:
    image: zookeeper
    restart: always
    hostname: zoo3
    ports:
      - 2183:2181
    environment:
      ZOO_MY_ID: 3
      ZOO_SERVERS: server.1=zoo1:2888:3888;2181 server.2=zoo2:2888:3888;2181 server.3=0.0.0.0:2888:3888;2181
```

### 启动zookeeper集群
* 方式1：docker-compose -f d:/usr/local/etc/zookeeper-cluster/docker-compose-zookeeper.yml up
* 方式2：
    * docker swarm init
    * docker stack deploy -c d:/usr/local/etc/zookeeper-cluster/docker-compose-zookeeper.yml zookeeper
    
### 安装nc命令
* apt-cyg install nc

### 连接集群
* nc -vz 192.168.1.110 2181

## kafka
### 拉取kafka镜像
* docker pull wurstmeister/kafka

### 编写启动配置文件：docker-compose-kafka.yml
```docker-compose-kafka.yml
version: '2'
services:
  zookeeper:
    image: zookeeper
    ports:
      - "2184:2181"
  kafka:
    image: wurstmeister/kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: 192.168.1.110
      KAFKA_ZOOKEEPER_CONNECT: 192.168.1.110:2184
```

#### 启动kafka
* docker-compose -f d:/usr/local/etc/kafka/docker-compose-kafka.yml up -d

## 相关命令

### 取消容器总是重启
* docker update --restart=no <container_id>

### docker swarm初始化
* docker swarm init

### 增加节点
* docker swarm join-token manager

### 节点MANAGER STATUS列说明

#### 显示节点是属于manager或者worker,没有值 表示不参与群管理的工作节点。
* Leader 意味着该节点是使得群的所有群管理和编排决策的主要管理器节点。
* Reachable 意味着节点是管理者节点正在参与Raft共识。如果领导节点不可用，则该节点有资格被选为新领导者。
* Unavailable 意味着节点是不能与其他管理器通信的管理器。如果管理器节点不可用，您应该将新的管理器节点加入群集，或者将工作器节点升级为管理器。

### 节点AVAILABILITY列说明：
#### 显示调度程序是否可以将任务分配给节点
* Active 意味着调度程序可以将任务分配给节点。
* Pause 意味着调度程序不会将新任务分配给节点，但现有任务仍在运行。
* Drain 意味着调度程序不会向节点分配新任务。调度程序关闭所有现有任务并在可用节点上调度它们。

### 节点修改帮助
* docker node  update --help

### 停用节点，该节点上的容器会迁移到其他节点
* docker node  update --availability drain node1

### 将节点降级
* docker node demote node1

### 删除节点，只能删除worker下的节点
* docker node rm node1

### 查看节点
* docker node ls

### 删除那些已停止的容器、dangling 镜像、未被容器引用的network和构建过程中的cache(默认不会删除那些未被任何容器引用的数据卷)
* docker system prune

### 删除dangling或所有未被使用的镜像
* docker image prune 

### 删除所有退出状态的容器
* docker container prune 

### 删除未被使用的数据卷
* docker volume prune 

### 注意：慎用，这个命令不仅会删除数据卷，而且连确认的过程都没有，使用--all参数后会删除所有未被引用的镜像，而不仅仅是dangling镜像
* docker system prune --all --force --volumes
