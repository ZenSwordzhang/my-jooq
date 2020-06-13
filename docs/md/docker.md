## 相关命令

### 从docker中拷贝文件到宿主机
* docker cp [OPTIONS] [CONTAINER_ID]:[SRC_PATH] [DEST_PATH]
* docker cp <container_id | container_name>:/xxx/xxx/xxx/common.log /home/yyy.log
* 例1： docker cp logstash:/usr/share/logstash/Gemfile d:/usr/local/opt/gem/Gemfile
* 例2： docker cp logstash:/usr/share/logstash/Gemfile.lock d:/usr/local/opt/gem/Gemfile.lock

### 查看容器日志
* docker logs -f --tail 10 container-name

### 取消容器总是重启
* docker update --restart=no <container_id>

### docker swarm初始化
* docker swarm init

### 增加节点
* docker swarm join-token manager

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

### network相关命令

#### 将容器连接到网络
* docker network connect

#### 创建一个网络
* docker network create [OPTIONS] NETWORK
* docker network create

#### 断开容器的网络
* docker network disconnect

#### 显示一个或多个网络的详细信息
* docker network inspect

#### 列出网络
* docker network ls

#### 删除所有未使用的网络
* docker network prune

#### 删除一个或多个网络
* docker network rm


### 注意：慎用，这个命令不仅会删除数据卷，而且连确认的过程都没有，使用--all参数后会删除所有未被引用的镜像，而不仅仅是dangling镜像
* docker system prune --all --force --volumes

## 参数说明
### 节点MANAGER STATUS列说明：显示节点是属于manager或者worker,没有值 表示不参与群管理的工作节点。
* Leader 意味着该节点是使得群的所有群管理和编排决策的主要管理器节点。
* Reachable 意味着节点是管理者节点正在参与Raft共识。如果领导节点不可用，则该节点有资格被选为新领导者。
* Unavailable 意味着节点是不能与其他管理器通信的管理器。如果管理器节点不可用，您应该将新的管理器节点加入群集，或者将工作器节点升级为管理器。

### 节点AVAILABILITY列说明：显示调度程序是否可以将任务分配给节点
* Active 意味着调度程序可以将任务分配给节点。
* Pause 意味着调度程序不会将新任务分配给节点，但现有任务仍在运行。
* Drain 意味着调度程序不会向节点分配新任务。调度程序关闭所有现有任务并在可用节点上调度它们。


## 参考资料

### 容器中的用户
[隔离 docker 容器中的用户](https://www.cnblogs.com/sparkdev/p/9614326.html)
