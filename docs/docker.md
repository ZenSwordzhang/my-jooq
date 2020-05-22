### 问题
#### 问题1：Got permission denied while trying to connect to the Docker daemon socket at unix:///var/run/docker.sock
* 背景：win10下docker拉取镜像报错
* 原因：缺少权限
* 解决方法：sudo chmod 666 /var/run/docker.sock

#### 问题2： Attempted to resurrect connection to dead ES instance, but got an error. {:url=>"http://127.0.0.1:9200/", :error_type=>LogStash::Outputs::ElasticSearch::HttpClient::Pool::HostUnreachableError, :error=>"Elasticsearch Unreachable: [http://127.0.0.1:9200/][Manticore::SocketException] Connection refused (Connection refused)"}
* 背景：win10下docker容器运行logstash镜像报错
* 原因：logstash.conf配置hosts地址错误
```logstash.conf
input { stdin { } }
output {
	elasticsearch { 
		hosts => ["127.0.0.1:9200"] 
		index => "zsx"  
		}
	stdout { codec => rubydebug }
}
```
* 解决方法：修改logstash.conf中hosts配置
```logstash.conf
input { stdin { } }
output {
	elasticsearch { 
        # 应该配置宿主机IP
		hosts => ["192.168.1.110:9200"] 
		index => "zsx"  
		}
	stdout { codec => rubydebug }
}
```

#### 问题3：Attempted to resurrect connection to dead ES instance, but got an error. {:url=>"http://elasticsearch:9200/", :error_type=>LogStash::Outputs::ElasticSearch::HttpClient::Pool::HostUnreachableError, :error=>"Elasticsearch Unreachable: [http://elasticsearch:9200/][Manticore::ResolutionFailure] elasticsearch: Name or service not known"}
* 背景：win10下docker容器运行logstash镜像报错
```docker
docker run --rm -it -v /d/usr/share/logstash/pipeline:/usr/share/logstash/pipeline -p 9600:9600 -p 5044:5044 logstash:7.6.2
```
* 原因：logstash镜像中logstash.yml默认配置为
```logstash.yml
http.host: "0.0.0.0"
xpack.monitoring.elasticsearch.hosts: [ "http://elasticsearch:9200" ]
```
* 解决：
    * 通过挂载修改容器logstash.yml文件配置
```logstash.yml
   http.host: "0.0.0.0"
```
    * 执行命令
```docker
docker run --rm -it -v /d/usr/share/logstash/config/logstash.yml:/usr/share/logstash/config/logstash.yml -v /d/usr/share/logstash/pipeline:/usr/share/logstash/pipeline -p 9600:9600 -p 5044:5044 logstash:7.6.2
```

####问题4：400 Bad Request 'json' or 'msgpack' parameter is required
背景：执行curl命令时报错
原因：win10下curl命令无法识别单引号
```
curl -X POST -d 'json={"json":"message"}' http://localhost:9880/sample.test
```
命令修改为(将单引号改为双引号，单引号内的双引号通过反斜杠\转义)
```
curl -X POST --data "json={\"msgpack\":\"message\"}" http://localhost:9880/sample.test
或
curl -X POST --header "Content-Type:application/json" --data-raw "{\"json\":{\"msgpack\": \"message\"}}" http://localhost:9880/sample.test
```

### 相关命令

#### 从docker中拷贝文件到宿主机
* docker cp [OPTIONS] [CONTAINER_ID]:[SRC_PATH] [DEST_PATH]
* docker cp <container_id | container_name>:/xxx/xxx/xxx/common.log /home/yyy.log
* 例1： docker cp logstash:/usr/share/logstash/Gemfile d:/usr/local/opt/gem/Gemfile
* 例2： docker cp logstash:/usr/share/logstash/Gemfile.lock d:/usr/local/opt/gem/Gemfile.lock

#### 查看容器日志
* docker logs -f --tail 10 container-name

#### 取消容器总是重启
* docker update --restart=no <container_id>

#### docker swarm初始化
* docker swarm init

#### 增加节点
* docker swarm join-token manager

#### 节点修改帮助
* docker node  update --help

#### 停用节点，该节点上的容器会迁移到其他节点
* docker node  update --availability drain node1

#### 将节点降级
* docker node demote node1

#### 删除节点，只能删除worker下的节点
* docker node rm node1

#### 查看节点
* docker node ls

#### 删除那些已停止的容器、dangling 镜像、未被容器引用的network和构建过程中的cache(默认不会删除那些未被任何容器引用的数据卷)
* docker system prune

#### 删除dangling或所有未被使用的镜像
* docker image prune 

#### 删除所有退出状态的容器
* docker container prune 

#### 删除未被使用的数据卷
* docker volume prune 

#### 注意：慎用，这个命令不仅会删除数据卷，而且连确认的过程都没有，使用--all参数后会删除所有未被引用的镜像，而不仅仅是dangling镜像
* docker system prune --all --force --volumes

### 参数说明
#### 节点MANAGER STATUS列说明：显示节点是属于manager或者worker,没有值 表示不参与群管理的工作节点。
* Leader 意味着该节点是使得群的所有群管理和编排决策的主要管理器节点。
* Reachable 意味着节点是管理者节点正在参与Raft共识。如果领导节点不可用，则该节点有资格被选为新领导者。
* Unavailable 意味着节点是不能与其他管理器通信的管理器。如果管理器节点不可用，您应该将新的管理器节点加入群集，或者将工作器节点升级为管理器。

#### 节点AVAILABILITY列说明：显示调度程序是否可以将任务分配给节点
* Active 意味着调度程序可以将任务分配给节点。
* Pause 意味着调度程序不会将新任务分配给节点，但现有任务仍在运行。
* Drain 意味着调度程序不会向节点分配新任务。调度程序关闭所有现有任务并在可用节点上调度它们。