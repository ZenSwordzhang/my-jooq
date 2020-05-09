### logstash [文档来源链接](https://www.jianshu.com/p/14b6ea9c6469)
* input
* filters
* codecs
* output

#### input ：数据输入端，可以接收来自任何地方的源数据。
* file：从文件中读取
* fsyslog：监听在514端口的系统日志信息，并解析成RFC3164格式。
* fkafka：从kakfka topic 中获取数据
* fbeat：接收来自Filebeat的事件

#### Filter ：数据中转层，主要进行格式处理，数据类型转换、数据过滤、字段添加，修改等，常用的过滤器如下。
* grok: 通过正则解析和结构化任何文本。Grok 目前是logstash最好的方式对非结构化日志数据解析成结构化和可查询化。
    * logstash内置了120个匹配模式，满足大部分需求[github链接地址](https://github.com/logstash-plugins/logstash-patterns-core/tree/master/patterns "github链接地址") 
* mutate: 在事件字段执行一般的转换。可以重命名、删除、替换和修改事件字段。
* drop: 完全丢弃事件，如debug事件。
* clone: 复制事件，可能添加或者删除字段。
* geoip: 添加有关IP地址地理位置信息。

#### output :是logstash工作的最后一个阶段，负责将数据输出到指定位置，兼容大多数应用，常用的有:
* elasticsearch: 发送事件数据到 Elasticsearch，便于查询，分析，绘图。
* file: 将事件数据写入到磁盘文件上。
* kafka: 将事件数据发送至高性能kafka分布式系统，便于临时存储，查询，分析，大数据消费场景。
* redis:将数据发送至redis-server，常用于中间层暂时缓存。
* influxdb: 发送指标数据到influxdb。
* statsd: 发送事件数据到 statsd

#### logstash.xml配置说明     
```logstash.xml
node.name：节点的描述性名称。默认值为机器的主机名

path.data：Logstash及其插件用于满足任何持久需求的目录，默认值LOGSTASH_HOME/data。

pipeline.id： 管道的ID，默认值为main，可以更改；

pipeline.workers:
并行执行管道过滤器和输出阶段的工作器数量。如果发现事件正在备份，或者CPU未饱和，请考虑增加此数量以更好地利用机器处理能力，默认值为主机的CPU核心数

pipeline.batch.size：
在尝试执行其过滤器和输出之前，单个工作线程将从输入收集的最大事件数。较大的批量通常更有效，但代价是增加了内存开销，需要在jvm.options配置文件中增加JVM堆空间，默认值为125。

pipeline.batch.delay：
创建管道事件批处理时，在将小型批处理分派给管道工作者之前等待每个事件的时间以毫秒为单位，默认值为50

pipeline.unsafe_shutdown：
设置true为时，强制Logstash在关闭期间退出，即使内存中仍有事件。默认情况下，Logstash将拒绝退出，直到所有已接收的事件都被推送到输出。启用此选项可能会导致关闭期间数据丢失，默认值为false

path.config：
主管道的Logstash配置的路径。如果指定目录或通配符，则会按字母顺序从目录中读取配置文件。

config.test_and_exit：
设置为时true，检查配置是否有效，然后退出。使用此设置不会检查grok模式的正确性。Logstash可以从目录中读取多个配置文件，默认值为false

config.reload.automatic：
设置true为时，定期检查配置是否已更改，并在配置发生更改时重新加载配置。这也可以通过SIGHUP信号手动触发，默认值为false

config.reload.interval：几秒钟内，Logstash会检查配置文件中的更改，默认值为3s

config.debug：
设置为时true，将完全编译的配置显示为调试日志消息。你还必须设置log.level:
debug。警告：日志消息将包含作为纯文本传递给插件配置的任何密码选项，并可能导致明文密码出现在您的日志中，默认值为false

config.support_escapes
设置true为时，带引号的字符串将处理以下转义序列：\n成为文字换行符（ASCII 10）。\r成为文字回车（ASCII13）。\t成为文字标签（ASCII 9）。\\成为一个字面反斜杠\。\"成为字面双引号。\'成为字面引号，默认值为false

modules
配置时，modules必须在此表中描述的嵌套YAML结构中。

queue.type：
用于事件缓冲的内部排队模型。指定memory基于内存的传统队列，或persisted基于磁盘的ACKed队列（持久队列），默认值为memory，推荐使用持久队列

path.queue：
启用持久队列时将存储数据文件的目录路径（queue.type: persisted）。

queue.page_capacity：
启用持久队列时使用的页面数据文件的大小（queue.type: persisted）。队列数据由分成页面的仅附加数据文件组成，默认值为64MB

queue.max_events：
启用持久队列时队列中未读事件的最大数量（queue.type: persisted），默认值为0（无限制）

queue.max_bytes：
队列的总容量，以字节数表示。确保磁盘驱动器的容量大于此处指定的值。如果同时指定了两者queue.max_events，queue.max_bytes则Logstash将使用先达到的标准，默认值为1024mb

queue.checkpoint.acks：
启用持久队列时强制检查点之前的最大ACK事件数（queue.type: persisted）。指定queue.checkpoint.acks: 0将此值设置为无限制，默认值为1024

queue.checkpoint.writes：
启用持久队列时强制检查点之前写入事件的最大数量（queue.type: persisted）。指定queue.checkpoint.writes:

0将此值设置为无限制，默认值为1024

queue.checkpoint.retry：
启用后，对于任何失败的检查点写入，Logstash将针对每次尝试检查点写入重试一次。不会重试任何后续错误。这是仅在具有非标准行为（如SAN）的文件系统上看到的失败检查点写入的解决方法，除特殊情况外，不建议这样做，默认值为false

dead_letter_queue.enable：
用于指示Logstash启用插件支持的DLQ功能的标志，默认值为false

dead_letter_queue.max_bytes：
每个死信队列的最大大小。如果条目超过此设置会增加死信队列的大小，则会删除条目。默认值为1024mb

path.dead_letter_queue：
将为死信队列存储数据文件的目录路径。

http.host：绑定地址，默认值为"127.0.0.1"

http.port：绑定端口，默认值为9600

log.level：日志级别。有效选项包括：fatal，error，warn，info，debug，trace，info

log.format：日志格式。设置为json或plain，默认值为plain

path.logs：Logstash将其日志写入的目录，默认值为LOGSTASH_HOME/logs

path.plugins：
哪里可以找到自定义插件。您可以多次指定此设置以包含多个路径。插件预计将在一个特定的目录层次结构： PATH/logstash/TYPE/NAME.rb其中TYPE是inputs，filters，outputs，或codecs，并且NAME是插件的名称。
```

#### input配置
* Beat：从beat采集
```input
input {
  beats {
    port => 5044
  }
}
```
* File：从文件采集
```input
input {
  file {
    path => "/var/log/*/*.log"
  }
}

```
* Elasticsearch：从es集群采集
```input
input {
  elasticsearch {
    hosts => "localhost"
    query => '{ "query": {"match": { "statuscode": 200 } }, "sort": ["_doc" ] }'
  }
}
```
* Kafka：从kafka采集
```input
input {
  kafka {
    bootstrap_servers =>"192.168.1.110:9999 "
    topics => ["test"]
    codec => "json"
    security_protocol =>"SASL_PLAINTEXT"
    sasl_mechanism => "PLAIN"
    jaas_path =>"/etc/logstash/kafka-client-jaas.conf"
    consumer_threads => 2
    group_id => "logstash-bigdata"
    auto_offset_reset => "latest"
    max_poll_records => "500"
    max_poll_interval_ms =>"30000"
    session_timeout_ms => "30000"
    request_timeout_ms => "60000"
    auto_commit_interval_ms =>"5000"
    check_crcs => "false"
    heartbeat_interval_ms =>"9000"
    partition_assignment_strategy =>"org.apache.kafka.clients.consumer.RoundRobinAssignor"
    }
}
```
* Jdbc：从数据库采集
```input
input {
  jdbc {
    jdbc_driver_library =>"mysql-connector-java-5.1.36-bin.jar"
    jdbc_driver_class =>"com.mysql.jdbc.Driver"
    jdbc_connection_string =>"jdbc:mysql://localhost:3306/mydb"
    jdbc_user => "mysql"
    parameters => {"favorite_artist" => "Beethoven" }
    schedule => "* * * * *"
    statement => "SELECT * from songswhere artist = :favorite_artist"
  }
}
```

#### Output配置
* Elasticsearch：输出至ES集群
```output
output {
if [log_type] == "tomcat" and([department] == "zxbi" or [department] == "jfbi") {
    elasticsearch {
        hosts =>["https://rjjd-node01:9200"]
        index =>"jfbi.prd-%{+YYYY.MM}"
        user => "beatuser"
        password =>"1iQ7w3LQlOhHR6Rg1iQ7w3L"
        http_compression => "true"
        sniffing => "false"
        ssl => true
        ssl_certificate_verification =>"true"
        cacert => "/etc/ssl/xxx_ca.crt"
        id => "zxbi"
        }
    }
}
```
* Influxdb：输出至influxdb数据库
```output
output {
  influxdb {
  host => "10.1.2.2:8086"
  data_points => “{ }”
  }
}
```
* File：输出至文件
```output
output {
file {
  path => “/tmp/stdout.log”
  codec => line { format => "customformat: %{message}"}
  }
}
```
* Opentsdb：输出至opentsdb
```output
output {
  opentsdb {
  host => "localhost"
  metrics => [“%{host}”, “%{hostname}”]
  port => “4242”
  }
}
```
* Stdout：输出至控制台
```output
output {
  stdout { codec => json }
}
```

#### Logstash解析
* 最常用的解析插件：grok、date、drop、geoip、json、kv、ruby、split、mutate
##### Grok：grok匹配模式语法为：%{SYNTAX:SEMANTIC:TYPE}
    + SYNTAX: 正则表达式、预定义的正则表达式名称
    - SEMANTIC: 标识符，标识匹配后的数据
    * TYPE: 可选的类型，目前支持int、float
        * 例如：NUMBER可以匹配3.14，IP可以匹配：192.168.1.110
* 案例：192.168.1.110 GET /index.html 11123 0.035
```匹配规则
input {
  file {
    path => "/var/log/http.log"
  }
}

filter {
  grok {
    match => { "message" =>"%{IP:client} %{WORD:method} %{URIPATHPARAM:request}%{NUMBER:bytes} %{NUMBER:duration}" }
  }
}
```
* 匹配结果
```
client: 192.168.1.110
method: GET
request: /index.html
bytes: 11123
duration: 0.035
```
* grok表达式支持的可选参数
```
break_on_match：默认为true代表匹配成功后则不匹配后面的表达式，false相反
keep_empty_captures：默认为false，字段没有值时则不输出字段，true则相反
match：表达式模式
overwrite：字段重新命名
patterns_dir：表达式文件夹
patterns_files_glob：表达式文件夹正则匹配
tag_on_failure：匹配失败后添加tag
tag_on_timeout：匹配超时添加tag
add_field：自定义添加字段
add_tag： 自定义添加tag
enable_metric：启用监控
id：表达式id
remove_field：移除字段
remove_tag：移除tag
```
##### Date：日期过滤器用于解析字段中的日期，然后使用该日期或时间戳作为事件的logstash时间戳
```filter
filter {
  date {
  match => [ "logdate", "MMM dd yyyyHH:mm:ss" ]
  target => “localtime”
  }
}
```
* Date表达式支持的参数
```
Locale：日期语言环境
Match：表达式
Timezone：时间时区
Tag_on_failure：匹配失败后添加tag
Target：匹配成功后目标字段
```
* 案例
```
2019-08 12:22:22 239 ---yyyy-MM HH:mm:ss SSS
Jul 2 2019 22:30 ---MMM d yyyy HH:mm
12 Mar 2019 22:30:30 +08:00 ---dd MMM yyyy HH:mm:ss ZZ
```

##### Drop：当不需要某些数据的时候，可以使用drop插件丢弃
```
filter {
  if [loglevel] == "debug" {
    drop { }
  }
}
```
##### Geoip
```
filter {
  geoip {
    database =>“/tmp/xx.db”
    fields => [“ip”,”longitute”,”city_name”]
    source => “ip”
    target => “geoip”
  }
}
```

##### Json
```
filter {
  json {
    source => "message"
  }
}
```
##### Kv：此过滤器有助于自动解析各种消息（或特定事件字段）类似foo=bar
```
filter {
  kv {
    default_keys => [“host”,”ip”]
    exclude_keys => [“times”]
    field_split => “&?”
  }
}
```
#### Ruby：执行ruby代码。此过滤器接受内联ruby代码或ruby文件。这两个选项是互斥的，具有稍微不同的工作方式。
```
filter {
  ruby {
    code => "event.cancel if rand <=0.90"
  }
}
```

#### Split
```
filter {
  split {
    field => "results"
    target=> “messages”
    terminator => “\n”
  }
}
```

#### Mutate：强大的mutate过滤器，可以对数据进行增删改查。支持的语法多，且效率高
* 按照执行顺序排列
```
coerce
rename：重命名字段
update：更新数据
replace：替换字段值
convert：转换字段类型
gsub：替换字符
uppercase：转为大写的字符串
capitalize：转换大写字符串
lowercase：转为小写的字符串
strip：剥离字符空白
remove：移除字段
split：分离字段
join：合并数组
merge：合并多个数组
copy：复制字段
```
* 案例
```
filter {
  mutate {
    split => ["hostname","."]
    add_field => {"shortHostname" => "%{hostname[0]}" }
  }
  mutate {
    rename =>["shortHostname", "hostname" ]
  }
}
```

### fluentd