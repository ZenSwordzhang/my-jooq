## 命令

### windows、ubuntu下连接redis单机
* docker exec -it redis-7000 redis-cli -h 127.0.0.1 -p 7000

### cygwin下连接redis单机
* npm install -g redis-cli
* rdcli -h 127.0.0.1 -p 7000

### ubuntu下连接redis单机
* sudo apt install redis-tools
* redis-cli -h 127.0.0.1 -p 7000

### 创建集群，分配主从节点（容器已经创建的前提下）
* 1.如果不能执行redis-cli命令，需要进入容器内部
    * docker exec -it redis-7000 bash 
* 2.集群创建
    * redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 --cluster-replicas 1

### 查看主从信息
* redis-cli -c -p 7000
* info replication

### 启动redis延迟监听
* CONFIG SET latency-monitor-threshold 100

### 查看redis指标信息（通过客户端连接到redis后执行命令）
* info
* info CPU

### 查看redis响应一个请求的时间
* latency latest
* 模拟数据
    * debug sleep 1

## redis性能指标

### 性能测试
* ./redis-benchmark -c 100 -n 5000
    * 100个连接，5000次请求对应的性能

### 监控指标
* 性能指标：Performance metrics
* 内存指标：Memory metrics
* 基础活动指标：Basic activity metrics
* 持久性指标：Persistence metrics
* 错误指标：Error metrics

#### 性能指标：Performance metrics
名称 | 描述 | 指标类型
:----: | :----: | :---:
latency | Redis服务器平均响应请求的时间 | Work: Performance
instantaneous_ops_per_sec |  每秒处理的命令总数 | Work: Throughput
hit rate (calculated) |  缓存命中率 keyspace_hits / (keyspace_hits + keyspace_misses) | Work: Success

##### latency（延迟）
* 检测命令：
    * redis-cli --latency -h 192.168.1.121 -p 6379
    * 本地redis 6379端口：redis-cli --latency
    * redis-cli --latency-history
* 检测结果：
    * min: 1, max: 623, avg: 4.38 (77795 samples)
* 结果解析：
    * min: 1 
        * redis-cli发出PING的时间到收到回复的时间之间的最小延迟
    * max: 623
        * redis-cli发出PING信号到收到命令的响应之间的最大延迟
    * avg: 4.38
        * 所有采样数据的平均响应时间(以毫秒为单位)
    77795 samples： redis-cli记录发出PING命令并接收响应的次数

##### instantaneous_ops_per_sec
* redis-cli info | grep instantaneous_ops_per_sec

##### hit rate


#### 内存指标：Memory metrics

名称 | 描述 | 指标类型
:----: | :----: | :---:
used_memory	| Redis使用的内存量（以字节为单位）	| Resource: Utilization
mem_fragmentation_ratio | 操作系统分配的内存与Redis请求的内存的比率 | Resource: Saturation
evicted_keys | 由于达到最大内存限制而删除的键数 | Resource: Saturation
blocked_clients	| 由于BLPOP、BRPOP或BRPOPLPUSH而阻塞的客户端 | Other

##### used_memory
* 查看内存使用情况命令
    * info memory

##### mem_fragmentation_ratio（内存碎片率）
* redis-cli info | grep mem_fragmentation_ratio
* 碎片比率大于1表示正在发生碎片
* 比率超过1.5表示碎片过多，您的Redis实例消耗了其请求的物理内存的150％
* 小于1的碎片率告诉您Redis需要的内存多于系统上可用的内存，这将导致交换

##### evicted_keys
* 如果将Redis用作缓存，则可能需要将其配置为在达到最大内存限制时自动清除键
* 如果将Redis用作数据库或队列，则最好将其替换为逐出，在这种情况下，可以跳过此指标

* 配置key的过期策略命令：
    * redis-cli CONFIG SET maxmemory-policy <policy>
    * <policy>的可选值
    * -noeviction 
        * 当达到内存限制并且用户尝试添加其他键时，返回错误
    * -volatile-lru （LRU算法：least recently used）
        * 从设置了过期时间的key值中，删除最近最少使用的key
    * -volatile-ttl 
        * 从设置了过期时间的key值中，删除剩余时间最短的key
    * -volatile-random 
        * 从设置了过期时间的key值中，随机删除一个key
    * -allkeys-lru
        * 从所有key值中，删除最近最少使用的key
    * -allkeys-random
        * 从所有key值中，随机删除一个key
    * -volatile-lfu （LFU算法:least frequently used）
        * 在Redis4中添加的功能，从设置了过期时间的key值中，删除最不常用的key
    * -allkeys-lfu 
        * 在Redis4中添加的功能，从所有key值中，删除最不常用的key

##### blocked_clients


#### 基础活动指标：Basic activity metrics

名称 | 描述 | 指标类型
:----: | :----: | :---:
connected_clients	| 连接到Redis的客户端数量	| Resource: Utilization
connected_slaves | 连接到当前主机的从机数量 | Other
master_last_io_seconds_ago | 主从最后一次交互的时间(单位：s) | Other
keyspace	| 数据库中的键总数 | Resource: Utilization


#### 持久性指标：Persistence metrics

名称 | 描述 | 指标类型
:----: | :----: | :---:
rdb_last_save_time | 最后保存到磁盘的Unix时间戳 | Other
rdb_changes_since_last_save | 自最后一次持久化以来数据库的更改数 | Other
* redis-cli info | grep rdb_last_save_time
* redis-cli info | grep rdb_changes_since_last_save

#### 错误指标：Error metrics

名称 | 描述 | 指标类型
:----: | :----: | :---:
rejected_connections | 由于达到最大客户端限制而被拒绝的连接数 | Resource: Saturation
keyspace_misses | 键查找失败的次数 | Other
master_link_down_since_seconds | 主从断开的时间（单位：s） | Resource: Errors``````````


## 参考链接

### 指标
* [redis监控指标](http://liangjf.top/2020/03/30/118.redis%E7%9B%91%E6%8E%A7%E6%8C%87%E6%A0%87/)
* [Redis监控](https://mp.weixin.qq.com/s?__biz=MzI4NTA1MDEwNg==&mid=2650788187&idx=1&sn=9363cc47966b4464e82f84904ac0b4b8&chksm=f3f964cec48eedd840c922263bfe39f31a8223d530cfac3a6bebd8b7bb0c0569712f087a65eb&scene=27#wechat_redirect)
* [如何监控Redis性能指标](https://www.datadoghq.com/blog/how-to-monitor-redis-performance-metrics/)
* [redis监控指标](https://www.jianshu.com/p/68485d5c7fb9)
* [Redis性能指标详解与监控](https://blog.csdn.net/z644041867/article/details/77965521)
* [metricbeat-module-redis](https://www.elastic.co/guide/en/beats/metricbeat/current/metricbeat-module-redis.html)
* [info](https://redis.io/commands/info)

### 性能
* [Redis性能问题排查](https://www.cnblogs.com/mushroom/p/4738170.html)

### redis module
* [redis module](https://www.elastic.co/guide/en/beats/filebeat/current/filebeat-module-redis.html)

### redis参数
* [redis参数](http://redisdoc.com/client_and_server/info.html)