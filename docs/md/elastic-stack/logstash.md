## logstash.conf配置说明

### 数据类型： 
```
布尔值类型: ssl_enable => true
字节类型: bytes => "1MiB"
字符串类型: name => "zsx"
数值类型: port => 22
数组: match => ["datetime","UNIX"]
哈希: options => {key1 => "value1",key2 => "value2"}
编码解码: codec => "json"
路径: file_path => "/tmp/filename"
注释: #
```

### 条件判断：
```
等于: ==
不等于: !=
小于: <
大于: >
小于等于: <=
大于等于: >=
匹配正则: =~
不匹配正则: !~
包含: in
不包含: not in 
与: and
或: or
非与: nand
非或: xor
复合表达式: ()
取反符合: !()
```

### file插件：
```
codec => #可选项，默认是plain，可设置其他编码方式。
discover_interval => #可选项，logstash多久检查一下path下有新文件，默认15s。
exclude => #可选项，排除path下不想监听的文件。
sincedb_path => #可选项，记录文件以及文件读取信息位置的数据文件。~/.sincedb_xxxx
sincedb_write_interval => #可选项，logstash多久写一次sincedb文件，默认15s.
stat_interval => #可选项，logstash多久检查一次被监听文件的变化，默认1s。
start_position => #可选项，logstash从哪个位置读取文件数据，默认从尾部，值为：end。初次导入，设置为：beginning。
path => #必选项，配置文件路径，可定义多个。
tags => #可选项，在数据处理过程中，由具体的插件来添加或者删除的标记。
type => #可选项，自定义处理时间类型。比如nginxlog
```

### tcp插件:
```
add_field => #可选项，默认{}。
codec => #可选项，默认plain。
data_timeout => #可选项，默认-1。
host => #可选项，默认0.0.0.0。
mode => #可选项，值为["server","client"]之一，默认为server。
port => #必选，端口。
ssl_cacert => #可选项，定义相关路径。
ssl_cert => #可选项，定义相关路径。
ssl_enable => #可选项，默认为false。
ssl_key => #可选项，定义相关路径。
ssl_key_passphrase => #可选项，默认nil
ssl_verify => #可选项，默认false。
tags => #可选项
type => #可选项
``` 

### udp插件：
``` 
udp插件字段解释：
add_field => #可选项，默认{}。
host => #可选项，默认0.0.0.0。
queue_size => #默认2000
tags => #可选项
type => #可选项
workers => #默认为2
``` 

### multiline插件：
``` 
charset => #字符编码，可选
max_bytes => #bytes类型，设置最大的字节数，可选
max_lines => #number类型，设置最大的行数，默认是500行，可选
multiline_tag => #string类型，设置一个事件标签，默认是"multiline" ，可选
pattern => #string 类型，设置匹配的正则表达式 ，必选 
patterns_dir => #array类型，可以设置多个正则表达式，可选
negate => #boolean类型，设置正向匹配还是反向匹配，默认是false，可选
what => #设置未匹配的内容是向前合并还是向后合并，previous, next 两个值选择，必选
``` 

### .json插件：
``` 
add_field => #hash（可选项），默认{}
add_tag => #array（可选项），默认[]
remove_field => #array（可选项），默认[]
remove_tag => #array（可选项），默认[]
source => #string（必选项）
target => #string（可选项）
``` 

### mutate插件
#### 重命名格式1
``` mutate
mutate {
    rename => { "host" => "[host][name]" }
}
``` 
#### 结果显示格式1
``` result

{
  "took" : 334,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 1,
      "relation" : "eq"
    },
    "max_score" : 1.0,
    "hits" : [
      {
        "_index" : "my_index",
        "_type" : "_doc",
        "_id" : "nhe2fnMBr8RDMA8CzsCc",
        "_score" : 1.0,
        "_source" : {
          "@timestamp" : "2020-07-24T02:46:16.263Z",
          "host" : {
            "name" : "2eff571f8ea1"
          },
          "@version" : "1"
        }
      }
    ]
  }
}
``` 
#### 重命名格式2
``` mutate
mutate {
    rename => { "host" => "host.name" }
}
```
#### 结果显示格式2 
``` result

{
  "took" : 334,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 1,
      "relation" : "eq"
    },
    "max_score" : 1.0,
    "hits" : [
      {
        "_index" : "my_index",
        "_type" : "_doc",
        "_id" : "nhe2fnMBr8RDMA8CzsCc",
        "_score" : 1.0,
        "_source" : {
          "@timestamp" : "2020-07-24T02:46:16.263Z",
          "host.name" : "2eff571f8ea1",
          "@version" : "1"
        }
      }
    ]
  }
}
``` 

## 命令

### 显示节点级别的JVM统计信息
* curl -XGET 'localhost:9600/_node/jvm?pretty'

### 显示操作系统信息
* curl -XGET 'localhost:9600/_node/os?pretty'

### 显示指定管道信息
* curl -XGET 'localhost:9600/_node/pipelines/test?pretty'

### 显示管道信息
* curl -XGET 'localhost:9600/_node/pipelines?pretty'

### 检索节点信息
* curl -XGET 'localhost:9600/_node/<types>'
* types：pipelines、os、jvm
* 例(用逗号分开)：curl -XGET 'localhost:9600/_node/pipelines,os,jvm?pretty'

### 查看logstash插件信息
* docker exec -it logstash bash
* 列出所有已安装插件：bin/logstash-plugin list 
* 列出所有已安装的插件，包括版本信息：bin/logstash-plugin list --verbose 
* 列出所有包括namefragment的已安装插件：bin/logstash-plugin list '*namefragment*' 
* 列出指定组的已安装插件(input, filter, codec, output)：bin/logstash-plugin list --group output 
* 安装插件（容器bin目录下）
    * logstash-plugin install logstash-output-kafka
* 查看插件
    * logstash-plugin list --verbose | grep kafka
* 更新插件
    * logstash-plugin update logstash-output-kafka
* 删除插件
    * logstash-plugin remove logstash-output-kafka


## 配置说明

### spring 默认配置：参考[链接](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-logging)
* ![](../img/log-01.jpg)
* A:日期和时间
* B:日志级别，分别有：ERROR、WARN、INFO、DEBUG、TRACE
* C:进程ID
* D 分隔符，后面的是日志消息
* E:线程的名称
* F:类名
* G:消息体
```
Date and Time: Millisecond precision and easily sortable.

Log Level: ERROR, WARN, INFO, DEBUG, or TRACE.

Process ID.

A --- separator to distinguish the start of actual log messages.

Thread name: Enclosed in square brackets (may be truncated for console output).

Logger name: This is usually the source class name (often abbreviated).

The log message.
```
* grok匹配
```
(?m)^%{TIMESTAMP_ISO8601:log_time}%{SPACE}%{LOGLEVEL:log_level}%{SPACE}%{NUMBER:pid}%{SPACE}---%{SPACE}%{SYSLOG5424SD:thread_name}%{SPACE}%{NOTSPACE:logger_name}%{SPACE}:%{SPACE}%{GREEDYDATA:log_msg}

(?m)^%{TIMESTAMP_ISO8601:log_time}%{SPACE}%{LOGLEVEL:log_level}%{SPACE}%{NUMBER:pid}%{SPACE}---%{SPACE}\[%{SPACE}%{USERNAME:thread_name}\]%{SPACE}%{USERNAME:logger_name}%{SPACE}:%{SPACE}%{GREEDYDATA:log_msg}
```
    
### input为udp/tcp
* 1. 启动命令：docker run -it -v /d/usr/local/etc/logstash/config/logstash.yml:/usr/share/logstash/config/logstash.yml -v /d/usr/local/etc/logstash/pipeline1:/usr/share/logstash/pipeline --name logstash1 -p 9601:9600 -p 5000:5000/udp -p 5000:5000 logstash:7.7.0
* 2. config目录下logstash.yml文件配置
```
http.host: "0.0.0.0"
```
* 3. pipeline目录下logstash.conf文件配置
```
input { 
    stdin { } 
    udp {
        host => "0.0.0.0"
        # 从5000端口获取日志
        port => 5000
        type => "udp"
    }
    tcp {
        mode => "server"
        host => "0.0.0.0"
        # 从5044端口取日志
        port => 5000
        # 需要安装logstash-codec-json_lines插件
        codec => json_lines
        type => "tcp"
    }
}

output {
	stdout { codec => rubydebug }
    if [type] == "udp" {
        elasticsearch {
            hosts => ["192.168.1.110:9200"]
            index => "logstash-udp-%{+YYYY.MM.dd}"
        }
    }
    if [type] == "tcp" {
        elasticsearch {
            hosts => ["192.168.1.110:9200"]
            index => "logstash-tcp-%{+YYYY.MM.dd}"
        }
    }
    
}

```
* 4. udp数据模拟发送脚本[send-udp.js](../../../node-js/send-udp.js)

## 参考链接

### 告警
* [subscriptions](https://www.elastic.co/cn/subscriptions)

### Configuring Security in Logstash
* [Configuring Security in Logstash](https://www.elastic.co/guide/en/logstash/current/ls-security.html)
* [configuration-management-settings](https://www.elastic.co/guide/en/logstash/current/configuring-centralized-pipelines.html)
* [configuring-logstash](https://www.elastic.co/guide/en/logstash/current/configuring-logstash.html)

### grok
* [patterns](https://github.com/logstash-plugins/logstash-patterns-core/tree/master/patterns)
* [grok](https://logz.io/blog/logstash-grok/)
* [在线调试工具](http://grokdebug.herokuapp.com/)

* ### 插件
* [插件下载地址](https://rubygems.org/)

### 第三方
* [logstash-best-practice-cn](https://doc.yonyoucloud.com/doc/logstash-best-practice-cn/index.html)

### springboot日志
* [springboot](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-logging)
