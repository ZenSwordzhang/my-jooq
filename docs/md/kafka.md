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

### 删除容器
* docker rm -f kafka-1 kafka-2 kafka-3 kafka-manager
