## kafka集群

### D:\usr\local\etc\docker-zookeeper-cluster目录下创建docker-zookeeper-cluster.yml文件
```docker-compose
version: '3'
services:
    zook-01:
        image: zookeeper:3.6.1
        restart: always
        container_name: zook-01
        ports:
            # client port
            - 2181:2181
            # follower port
            - 2888:2888
            # election port
            - 3888:3888
        environment:
            # this zookeeper's id, and others zookeeper node distinguishing
            ZOO_MY_ID: 1
            # zookeeper services list
            ZOO_SERVERS: ${ZOO_SERVERS}
        # 单台主机不能设置为host，默认网络驱动为bridge
        # network_mode: host
    zook-02:
        image: zookeeper:3.6.1
        restart: always
        container_name: zook-02
        ports:
            - 2182:2181
            - 2889:2888
            - 3889:3888
        environment:
            ZOO_MY_ID: 2
            ZOO_SERVERS: ${ZOO_SERVERS}
        # network_mode: host
    zook-03:
        image: zookeeper:3.6.1
        restart: always
        container_name: zook-03
        ports:
            - 2183:2181
            - 2890:2888
            - 3890:3888
        environment:
            ZOO_MY_ID: 3
            ZOO_SERVERS: ${ZOO_SERVERS}
        # network_mode: host
```

### D:\usr\local\bin\start目录下创建shell脚本docker-zeekeeper-cluster-up.sh，文本格式为Unix
```shell
#!/bin/bash

ZOO_SERVERS=server.1=192.168.1.110:2888:3888;2181 server.2=192.168.1.110:2889:3889;2182 server.3=192.168.1.110:2890:3890;2183
export ZOO_SERVERS
docker-compose -f D:/usr/local/etc/docker-zookeeper-cluster/docker-zookeeper-cluster.yml up -d
```

### 切换到脚本启动目录
* cd D:/usr/local/bin/start

### 赋予脚本文件可执行权限
* chmod +x docker-zeekeeper-cluster-up.sh

### 执行脚本（二选一）
* 方式1：./docker-zeekeeper-cluster-up.sh
* 方式2：bash docker-zeekeeper-cluster-up.sh
