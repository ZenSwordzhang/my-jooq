## 问题

### 问题1：Logstash not reading file in windows
* 参考地址：[链接](https://discuss.elastic.co/t/logstash-not-reading-file-in-windows/41723 "链接")

### 问题2：Logstash接收不到udp/tcp发送的数据
* 背景：用python脚本编写了udp/tcp服务器端与客户端，并启动了服务器端与客户端
* 原因：消息优先被发送到服务端了，Logstash监听的端口无法收到数据
* 解决：只能启动客户端，不能启动服务端

### 问题3： Don't know how to handle `Java::JavaLang::IllegalStateException
* 问题详情
```
[FATAL][logstash.runner          ] An unexpected error occurred! {:error=>#<LogStash::Error: Don't know how to handle `Java::JavaLang::IllegalStateException` for `PipelineAction::C
reate<main>`>, :backtrace=>["org/logstash/execution/ConvergeResultExt.java:109:in `create'", "org/logstash/execution/ConvergeResultExt.java:37:in `add'", "/usr/share/logstash/logstash-core/lib/logstash/age
nt.rb:339:in `block in converge_state'"]}
```
* 问题背景：在logstash.conf中添加了filter功能后，重启logstash失败
```
filter {
   multiline {
      pattern => "^(%{TIMESTAMP_ISO8601})"
      negate => true
      what => "previous"
   }
   grok {
      # Do multiline matching with (?m) as the above mutliline filter may add newlines to the log messages.
      match => [ "message", "(?m)^%{TIMESTAMP_ISO8601:log_time}%{SPACE}%{LOGLEVEL:log_level}%{SPACE}%{NUMBER:pid}%{SPACE}---%{SPACE}%{SYSLOG5424SD:thread_name}%{SPACE}%{NOTSPACE:logger_name}%{SPACE}:%{SPACE}%{GREEDYDATA:log_msg}" ]
   }
}
```
* 原因：logstash容器没有安装logstash-filter-multiline插件
* 解决：
    * 安装logstash-filter-multiline插件：logstash-plugin install logstash-filter-multiline

### 问题3：Could not execute action: PipelineAction::Create
* 问题详情：
```
][ERROR][logstash.agent           ] Failed to execute action {:id=>:main, :action_type=>LogStash::ConvergeResult::FailedAction, :message=>"Could not execu
te action: PipelineAction::Create<main>, action_result: false", :backtrace=>nil}
```
* 问题背景：logstash用filebeat收集日志时，logstash.conf文件中配置了codec为多行，logstash启动后自动退出
```
beats {
    port => 5044
    type => "filebeat"
    codec => multiline {
        pattern => "^(%{TIMESTAMP_ISO8601})"
        negate => true
        what => "previous"
    }
}
```
* 解决：注释掉logstash.conf文件中的codec配置，在filebeat.yml中添加多行匹配
```
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /usr/share/filebeat/logs/*.log
  # 自定义属性
  fields:
    env: test
  # 设置为true，则自定义env字段将作为顶级字段存储在输出文档中，而不是分组在fields子词典下
  fields_under_root: true
  
  # 多行合并参数，正则表达式，匹配时间格式：2020-05-25 03:39:08.167
  multiline.pattern: '^\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2}.\d{3}'
  # true 或 false；默认是false，匹配pattern的行合并；true，不匹配pattern的行合并
  multiline.negate: true
  # after 或 before，合并到上一行的末尾或下一行的开头
  multiline.match: after
```



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