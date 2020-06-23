# <h1 style="text-align: center;"> ----------**问题集合**---------- </h1>



## <h2 style="text-align: center;"> ------------------**CYGWIN**------------------ </h2>

### 问题1： /bin/sh: pkg-config: 未找到命令
* 背景：cygwin下执行命令make && make install安装redis报错
* 解决：通过启动setup-x86_64.exe执行文件安装pkg-config



## <h2 style="text-align: center;"> ------------------**DOCKER**------------------ </h2>

### 问题：400 Bad Request 'json' or 'msgpack' parameter is required
* 背景：执行curl命令时报错
* 原因：win10下curl命令无法识别单引号
* 解决：
```
curl -X POST -d 'json={"json":"message"}' http://localhost:9880/sample.test
```
命令修改为(将单引号改为双引号，单引号内的双引号通过反斜杠\转义)
```
curl -X POST --data "json={\"msgpack\":\"message\"}" http://localhost:9880/sample.test
或
curl -X POST --header "Content-Type:application/json" --data-raw "{\"json\":{\"msgpack\": \"message\"}}" http://localhost:9880/sample.test
```

### 问题： Attempted to resurrect connection to dead ES instance, but got an error. {:url=>"http://127.0.0.1:9200/", :error_type=>LogStash::Outputs::ElasticSearch::HttpClient::Pool::HostUnreachableError, :error=>"Elasticsearch Unreachable: [http://127.0.0.1:9200/][Manticore::SocketException] Connection refused (Connection refused)"}
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

### 问题：Attempted to resurrect connection to dead ES instance, but got an error. {:url=>"http://elasticsearch:9200/", :error_type=>LogStash::Outputs::ElasticSearch::HttpClient::Pool::HostUnreachableError, :error=>"Elasticsearch Unreachable: [http://elasticsearch:9200/][Manticore::ResolutionFailure] elasticsearch: Name or service not known"}
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


### 问题： Error response from daemon: network redis-net not found
* 背景：通过shell脚本创建redis集群时报错[redis-node-start](../docker/cluster/redis/redis-node-start.sh)
* 解决：
    * 添加创建网络命令：docker network create redis-net


### 问题：Error response from daemon: Cannot restart container 2ce6c5824ea5: OCI runtime create failed: container with id exists: 2ce6c5824ea58491f9bcd4782d00061fb01bd2c1e51af3d4d3d52aa679b5cee5: unknown
* 背景：wsl2重启电脑后重启容器报错
* 解决：重启wsl2


### 问题：Error response from daemon: dial unix /mnt/wsl/docker-desktop/shared-sockets/guest-services/docker.sock: connect: no such file or directory
* 背景：wsl2中执行docker ps命令报错
* 解决：
    * 1.查看服务状态：sudo service docker status
    * 2.启动docker服务：sudo service docker start
* [参考链接](https://forums.docker.com/t/cannot-connect-to-the-docker-daemon-at-unix-var-run-docker-sock/80886/3)
![](../img/docker/docker-02.jpg)


### 问题：Error response from daemon: Get https://registry-1.docker.io/v2/: net/http: request canceled while waiting for connection (Client.Timeout exceeded while awaiting headers)
* 背景：wsl2中docker拉取镜像时报错
* 解决：重启docker for windows


### 问题：Got permission denied while trying to connect to the Docker daemon socket at unix:///var/run/docker.sock
* 背景：win10下docker拉取镜像报错
* 原因：缺少权限
* 解决方法：sudo chmod 666 /var/run/docker.sock


### 问题：WSL distro stopped while waiting for Lifecycle server.
* 背景：win10开机启动docker for windows时报错
* 错误详情
```
WSL distro stopped while waiting for Lifecycle server.
  >Stdout:

  >Stderr:
2020/06/13 00:50:40 resolving /mnt/host/c/Program Files/Docker/Docker/resources/wsl/docker-wsl-cli.iso...
Error: mounting wslCLIDest: stat /mnt/host/c/Program Files/Docker/Docker/resources/wsl/docker-wsl-cli.iso: no such file or directory
2020/06/13 00:50:40 stat /mnt/host/c/Program Files/Docker/Docker/resources/wsl/docker-wsl-cli.iso: no such file or directory
mounting wslCLIDest
main.doRun
	/go/src/github.com/docker/pinata/linuxkit/pkg/wsl-bootstrap/main.go:224
main.run.func1
	/go/src/github.com/docker/pinata/linuxkit/pkg/wsl-bootstrap/main.go:83
github.com/docker/pinata/vendor/github.com/spf13/cobra.(*Command).execute
	/go/src/github.com/docker/pinata/vendor/github.com/spf13/cobra/command.go:762
github.com/docker/pinata/vendor/github.com/spf13/cobra.(*Command).ExecuteC
	/go/src/github.com/docker/pinata/vendor/github.com/spf13/cobra/command.go:852
github.com/docker/pinata/vendor/github.com/spf13/cobra.(*Command).Execute
	/go/src/github.com/docker/pinata/vendor/github.com/spf13/cobra/command.go:800
main.main
	/go/src/github.com/docker/pinata/linuxkit/pkg/wsl-bootstrap/main.go:25
runtime.main
	/usr/local/go/src/runtime/proc.go:203
runtime.goexit
	/usr/local/go/src/runtime/asm_amd64.s:1357
```
![](../img/docker/docker-01.jpg)
    * [参考链接](https://github.com/docker/for-win/issues/6812)
    * [参考链接](https://github.com/docker/for-win/issues/6822)
* 可能的解决：
    * In my case the problem was caused because the HyperV VM "DockerDesktopVM" was running. After shutting down the Docker Desktop VM works properly again through WSL2.



## <h2 style="text-align: center;"> ------------------**ES**------------------ </h2>

### 问题：has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
* 背景：elasticsearch无法连接到http://localhost:9200
* 解决：elasticsearch的配置文件elasticsearch.yml中新增配置
```
# elasticsearch-head插件访问需要配置
http.cors.enabled: true
http.cors.allow-origin: "*"
```
* 参考链接：[链接](https://github.com/mobz/elasticsearch-head#enable-cors-in-elasticsearch)


### 问题：max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
* 背景：ubuntu下elasticsearch容器启动后自动退出
* 解决：
    * 1.在/etc/sysctl.conf文件中添加vm.max_map_count=262144
    * 2.执行命令重新加载配置：sudo sysctl --system


### 问题：vendor.js:7829 POST http://localhost:9200/_all/_search 406 (Not Acceptable)
* 背景：elasticsearch-head查看数据时报406错误码
* 解决
```
1、进入head安装目录；docker exec -it elasticsearch-head bash
2、打开文件夹_site，cd _site/
3、编辑vendor.js  共有两处
* 6886行   contentType: "application/x-www-form-urlencoded
改成
contentType: "application/json;charset=UTF-8"
* 7573行 var inspectData = s.contentType === "application/x-www-form-urlencoded" &&
改成
var inspectData = s.contentType === "application/json;charset=UTF-8" &&
```



## <h2 style="text-align: center;"> ------------------**IDEA**------------------ </h2>

### 问题：
* 详情：Passed value of header "Host" is not allowed. Please contact your license server administrator.
* 原因：That's because the license server is running behind a reverse proxy. Please configure virtual hosts using the JLS_VIRTUAL_HOSTS variable.



## <h2 style="text-align: center;"> ------------------**JDK**------------------ </h2>

### 问题：Could not target platform: 'Java SE 14' using tool chain: 'JDK 11 (11)'.
* 解决方法：
    * [步骤一](../img/jdk/jdk-setting-1.jpg)
    * [步骤二](../img/jdk/jdk-setting-2.jpg)
    * [步骤三](../img/jdk/jdk-setting-3.jpg)
    * [步骤四](../img/jdk/jdk-setting-4.jpg)


### 问题： Process 'command 'E:\jdk\jdk-14.0.1\bin\java.exe'' finished with non-zero exit value -1
* 原因：配置了properties属性
```
jdbc {
    driver = 'org.postgresql.Driver'
    url = 'jdbc:postgresql://localhost:5432/my-jooq'
    user = 'postgres'
    password = '1234'
    properties {
        property {
            key = 'ssl'
            value = 'true'
        }
    }
}
```
* 解决方法：移除该属性值配置
```
jdbc {
    driver = 'org.postgresql.Driver'
    url = 'jdbc:postgresql://localhost:5432/my-jooq'
    user = 'postgres'
    password = '1234'
}
```



## <h2 style="text-align: center;"> ------------------**JOOQ**------------------ </h2>

### 问题：Could not get unknown property 'generateMyJooqSchemaSource' for task set of type org.gradle.api.internal.tasks.DefaultTaskContainer
* 任务名称不正确，修改为跟jooq插件名称一致
* [任务名称不正确](../img/jooq-1.jpg)



## <h2 style="text-align: center;"> ------------------**KAFKA**------------------ </h2>

### 问题：this node is not a swarm manager. Use "docker swarm init" or "docker swarm join" to connect this node to swarm and try again
* 背景：使用docker-compose命令启动kafka集群报错
* 解决：docker swarm init



## <h2 style="text-align: center;"> ------------------**KIBANA**------------------ </h2>

### 问题：No cached mapping for this field / refresh of fields not working
* 解决：
    * 进入[kibana管理界面](http://localhost:5601/)
    * 选择Management-》Index Patterns-》从列表中选择index pattern-》点击refresh按钮
    * ![](../img/kibana-01.jpg)


### 问题：Timelion不再出现在侧面导航中编辑
* 解决
    * 影响：要创建Timelion可视化，请转到“可视化”，然后从可视化类型中选择“ Timelion ”
    * 如果想在侧面导航中重新添加Timelion，在kibana.yml文件中设置timelion.ui.enabled为true
* 参考：https://www.elastic.co/guide/en/kibana/7.x/breaking-changes-7.0.html


### 问题：Timelion not showing any data
```原配置
.es(index=test-facility-metrics-host-*,
    timefield=@timestamp,
    metric=max:system.network.in.bytes)
```
* 参考：https://discuss.elastic.co/t/timelion-not-displaying/115503
* 原因：默认图表中的线需要两个点，每个线段一个起点，一个终点。 
    * 如果9:26-9:28存储桶中有数据，而9:28-9:30存储桶中没有数据，则timelion无法画线
* 解决：添加.fit(mode = nearest)
```修改后配置
.es(index=test-facility-metrics-host-*,
    timefield=@timestamp,
    metric=max:system.network.in.bytes)
.fit(mode = nearest)
```

## <h2 style="text-align: center;"> ------------------**LOG**------------------ </h2>

### 问题
* 详情：You might be seeing this error because you're using the wrong Compose file version
```
ERROR: Version in ".\create-certs.yml" is unsupported. You might be seeing this error because you're using the wrong Compose file version. Either specify a supported version (e.g "2.2"
 or "3.3") and place your service definitions under the `services` key, or omit the `version` key and place your service definitions at the root of the file to use version 1.
For more on the Compose file format versions, see https://docs.docker.com/compose/compose-file/
```
* 背景：win10下使用docker-compose3.8版本报错
    * Docker version 19.03.8,
    * docker-compose version 1.25.4
* 原因：docker-compose版本等级不够，注：docker 19.03.0+、docker-compose 1.25.5+
* 解决：升级docker-compose到1.25.5
    * 1. 打开PowerShell
    * 2. 执行命令：
    ```
    Invoke-WebRequest "https://github.com/docker/compose/releases/download/1.25.5/docker-compose-Windows-x86_64.exe" -UseBasicParsing -OutFile $Env:ProgramFiles\Docker\Docker\resources\bin\docker-compose.exe
    ```
* 参考：
    * [docker-version](https://docs.docker.com/compose/compose-file/#volume-configuration-reference)
    * [docker-compose-version](https://github.com/docker/compose/releases/)



## <h2 style="text-align: center;"> ------------------**LOGSTASH**------------------ </h2>

### 问题：Could not execute action: PipelineAction::Create
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


### 问题： Don't know how to handle `Java::JavaLang::IllegalStateException
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


### 问题：Logstash not reading file in windows
* 参考地址：[链接](https://discuss.elastic.co/t/logstash-not-reading-file-in-windows/41723 "链接")


### 问题：Logstash接收不到udp/tcp发送的数据
* 背景：用python脚本编写了udp/tcp服务器端与客户端，并启动了服务器端与客户端
* 原因：消息优先被发送到服务端了，Logstash监听的端口无法收到数据
* 解决：只能启动客户端，不能启动服务端



## <h2 style="text-align: center;"> ------------------**METRICBEAT**------------------ </h2>

### 问题：Exiting: error loading config file: config file ("metricbeat.yml") must be owned by the user identifier (uid=0) or root
* 背景：docker容器启动metricbeat时报错
* 原因：文件拥有者的问题
* [参考链接](https://www.elastic.co/guide/en/beats/libbeat/7.8/config-file-permissions.html)
* 解决：
    * 方法1：直接修改配置文件metricbeat.yml权限拥有者为root用户
    * 方法2：禁用严格权限检查（一般不推荐）
        * command: ["--strict.perms=false"]


### 问题：Exiting: error loading config file: config file ("metricbeat.yml") can only be writable by the owner but the permissions are "-rwxrwxr-x" (to fix the permissions use: 'chmod go-w /usr/share/metricbeat/metricbeat.yml')
* 背景：docker容器启动metricbeat时报错
* 原因：文件写的权限问题
* [参考链接](https://www.elastic.co/guide/en/beats/libbeat/7.8/config-file-permissions.html)
* 解决：
    * 方法1：直接修改配置文件metricbeat.yml读写权限，只让拥有者有写的权限
    * 方法2：禁用严格权限检查（一般不推荐）
        * command: ["--strict.perms=false"]


### 问题：ERROR: for metricbeat  Cannot start service metricbeat: cgroups: cannot find cgroup mount destination: unknown
* 背景：ws2下通过shell脚本执行docker-compose.yml文件启动metricbeat容器时报错
![](../img/docker/docker-04.jpg)
* [参考链接](https://github.com/microsoft/WSL/issues/4189)
* 解决：
    * sudo mkdir /sys/fs/cgroup/systemd
    * sudo mount -t cgroup -o none,name=systemd cgroup /sys/fs/cgroup/systemd



### 问题：ERROR: for metricbeat  Cannot create container for service metricbeat: dial unix /mnt/wsl/docker-desktop/shared-sockets/guest-services/docker.sock: connect: no such file or directory
* 背景：ws2下通过shell脚本执行docker-compose.yml文件启动metricbeat容器时报错
![](../img/docker/docker-03.jpg)
* 解决：
    * 步骤1：修复docker
        * 由于此处使用的docker for windows作为wsl2的docker容器，从图片当中可以看出此处docker命令已经失效，
            * 首先尝试重启docker for windows
            * 其次尝试重启wsl2后，再重启docker for windows
            * 最后再尝试重启电脑
        * 如果此处使用的是wsl2中单独安装的docker容器，那么此处重启wsl2中的docker服务
    * 步骤2：以root用户执行shell脚本
        * sudo -S ./docker-up.sh
        

### 问题：Got permission denied while trying to connect to the Docker daemon socket at unix:///var/run/docker.sock: Get http://%2Fvar%2Frun%2Fdocker.sock/v1.24/info: dial unix /var/run/docker.sock: connect: permission denied
* 背景：metricbeat收集docker容器指标时提示错误
* [参考链接](https://discuss.elastic.co/t/how-to-use-metricbeat-docker-module-in-docker-swarm/103019/3)
* [参考链接](https://discuss.elastic.co/t/trouble-running-metricbeat-5-3-0-with-docker-module/82204)
* [参考链接](https://discuss.elastic.co/t/issue-with-metricbeat-docker-container/109525)
* [参考链接](https://github.com/elastic/beats/issues/7526)
* 解决：
    * 方法1：使用文件系统ACL对Metricbeat用户授予的显式访问权限(未验证成功)
        * setfacl -m u:1000:rw /var/run/docker.sock
        * 1.查看文件系统(从图片可以看出为ext4文件系统，支持ACL):
            * df -T
        ![](../img/filesystem/filesystem-01.jpg)
        * 2.查看是否支持ACL: 
            * sudo tune2fs -l /dev/sda* |grep "Default mount options:"
        ![](../img/filesystem/filesystem-02.jpg)
        * 3.切换为root用户: 
            * sudo -s
        * 4.查看文件系统信息：
            * dumpe2fs -h /dev/sda*
        ![](../img/filesystem/filesystem-03.jpg)
        * 5.获取文件或目录的ACL设置信息:
            * getfacl /var/run/docker.sock
        ![](../img/filesystem/filesystem-04.jpg)
        * 6.设置文件或目录的ACL设置信息:
            * setfacl -m u:123:rw /var/run/docker.sock
        ![](../img/filesystem/filesystem-05.jpg)
        * 7.重启容器
            * docker restart metricbeat
    * 方法2：修改权限
        * sudo chmod 666 /var/run/docker.sock
    * 方法3：给容器指定root用户
```docker-compose.yml修改前
version: '3'

services:
  metricbeat:
    image: elastic/metricbeat:7.7.0
    container_name: metricbeat
    command: ["--strict.perms=false"]
    volumes:
      - ${PWD}/config/metricbeat.yml:/usr/share/metricbeat/metricbeat.yml
      - /var/run/docker.sock:/var/run/docker.sock:ro
      - /sys/fs/cgroup:/hostfs/sys/fs/cgroup:ro
      - /proc:/hostfs/proc:ro
      - /:/hostfs:ro
      - /etc/timezone:/etc/timezone:ro
      - /etc/localtime:/etc/localtime:ro
```
```docker-compose.yml修改后
version: '3'

services:
  metricbeat:
    image: elastic/metricbeat:7.7.0
    user: root
    container_name: metricbeat
    command: ["--strict.perms=false"]
    volumes:
      - ${PWD}/config/metricbeat.yml:/usr/share/metricbeat/metricbeat.yml
      - /var/run/docker.sock:/var/run/docker.sock:ro
      - /sys/fs/cgroup:/hostfs/sys/fs/cgroup:ro
      - /proc:/hostfs/proc:ro
      - /:/hostfs:ro
      - /etc/timezone:/etc/timezone:ro
      - /etc/localtime:/etc/localtime:ro
```

## <h2 style="text-align: center;"> ------------------**PYTHON**------------------ </h2>

### 问题
* 详情
```
b'A request was sent to this URL (http://localhost:8088/user) but a redirect was issued automatically by the routing system to "http://localhost:8088/user/".  The URL was defined with a trailing slash so Flask will automatically redirect to the URL with the trailing slash if it was accessed without one.  Make sure to directly send your POST-request to this URL since we can\'t make browsers or HTTP clients redirect with form data reliably or without user interaction.\n\nNote: this exception is only raised in debug mode'
```
* 背景：通过postman，执行http://localhost:8088/user发送post请求时报错
* 解决(注：末尾斜杠)：http://localhost:8088/user/



## <h2 style="text-align: center;"> ------------------**POSTGRES**------------------ </h2>

### 问题：FATAL:  data directory "/var/lib/postgresql/data" has wrong ownership
* 详情：
```log
2020-05-28 03:49:42.637 UTC [82] HINT:  The server must be started by the user that owns the data directory.
postgres   | child process exited with exit code 1
postgres   | initdb: removing contents of data directory "/var/lib/postgresql/data"
postgres exited with code 1

```
* 背景：win10下docker 启动postgres容器报错
```docker-compose-postgres.yml
version: '3.1'

services:
  # 服务器名
  db:
    image: postgres:13
    container_name: postgres
    # restart: always
    command: -c 'config_file=/etc/postgresql/postgresql.conf'
    volumes:
        - /d/usr/share/postgresql/data:/var/lib/postgresql/data
        - /d/usr/share/postgresql/log:/var/log/postgresql
        # # 配置文件默认路径 /var/lib/postgresql/data/postgresql.conf
        - /d/usr/share/postgresql/my-postgres.conf:/etc/postgresql/postgresql.conf
    environment:
      POSTGRES_USER: test
      POSTGRES_PASSWORD: test
      # POSTGRES_DB: test
    ports:
      - 5433:5432
      
  adminer:
    image: adminer:4.7.7-standalone
    container_name: adminer
    # restart: always
    ports:
      - 8080:8080
volumes:
  pgdata:
     external: true

```
* 原因：postgres必须以目录的所有者身份运行（无论读写是否成功），并且不能以root身份运行
* 解决：
    * 1.手动创建数据卷：docker volume create --name=pgdata
    * 2.挂载数据卷部分修改
    ```
    - /d/usr/share/postgresql/data:/var/lib/postgresql/data
    修改为：
    - pgdata:/var/lib/postgresql/data
    ```
    * 3.文件末尾部分声明数据卷外部可用
    ```
    volumes:
      pgdata:
         external: true
    ```
    * 修改后的文件内容如下
    ``` docker-compose-postgres.yml
    version: '3.1'
    
    services:
      # 服务器名
      db:
        image: postgres:13
        container_name: postgres
        # restart: always
        command: -c 'config_file=/etc/postgresql/postgresql.conf'
        volumes:
            - pgdata:/var/lib/postgresql/data
            - /d/usr/share/postgresql/log:/var/log/postgresql
            # # 配置文件默认路径 /var/lib/postgresql/data/postgresql.conf
            - /d/usr/share/postgresql/my-postgres.conf:/etc/postgresql/postgresql.conf
        environment:
          POSTGRES_USER: test
          POSTGRES_PASSWORD: test
          # POSTGRES_DB: test
        ports:
          - 5433:5432
          
      adminer:
        image: adminer:4.7.7-standalone
        container_name: adminer
        # restart: always
        ports:
          - 8080:8080
    volumes:
      pgdata:
         external: true
    
    ```
* 参考
    * [链接1](https://forums.docker.com/t/data-directory-var-lib-postgresql-data-pgdata-has-wrong-ownership/17963)
    * [链接2](https://github.com/graphprotocol/graph-node/issues/755)


### 问题：ERROR: Named volume "pgdata:/var/lib/postgresql/data:rw" is used in service "db" but no declaration was found in the volumes section.
* 背景：win10下docker 启动postgres容器报错
```docker-compose-postgres.yml
version: '3.1'

services:
  # 服务器名
  db:
    image: postgres:13
    container_name: postgres
    # restart: always
    command: -c 'config_file=/etc/postgresql/postgresql.conf'
    volumes:
        - pgdata:/var/lib/postgresql/data
        - /d/usr/share/postgresql/log:/var/log/postgresql
        # # 配置文件默认路径 /var/lib/postgresql/data/postgresql.conf
        - /d/usr/share/postgresql/my-postgres.conf:/etc/postgresql/postgresql.conf
    environment:
      POSTGRES_USER: test
      POSTGRES_PASSWORD: test
      # POSTGRES_DB: test
    ports:
      - 5433:5432
```
* 原因：缺少数据声明外部可用
* 解决：
    * 文件末尾部分声明数据卷外部可用
    ```
    volumes:
      pgdata:
         external: true
    ```



## <h2 style="text-align: center;"> ------------------**RUBY**------------------ </h2>

### 问题：RuboCop: Carriage return character missing. [Layout/EndOfLine]
* 背景：win10下ruby文件提示语法警告
解决：
    * 方法1：
        * window下要选择文本格式为CRLF-window，Linux下选择LF-Unix，如：[ruby-01.jpg](../../my-ruby/ruby-01.jpg)
    * 方法2：E:\Ruby27-x64\lib\ruby\gems\2.7.0\gems\rubocop-0.83.0\config\default.yml文件修改如下配置
    ```
    Style/EndOfLine:
       # 注：Linux为lf，windows为crlf
       EnforcedStyle: lf
    ```
  * 文件末尾要空一行


### 问题：RuboCop: Missing frozen string literal comment. [Style/FrozenStringLiteralComment]
* 背景：win10下ruby文件提示语法警告
* 解决：文件添加内容行：# frozen_string_literal: true


### 问题：RuboCop: The name of this source file (`first-ruby.rb`) should use snake_case. [Naming/FileName]
* 背景：win10下ruby文件提示语法警告
* 解决：修改文件名：其中“-”修改为"_"



## <h2 style="text-align: center;"> ------------------**REDIS**------------------ </h2>

### 问题：Could not connect to Redis at 192.168.1.110:7000: Connection refused
* 背景：wsl2中使用shell脚本创建redis集群时报错
* 解决：使用wsl2自身的ip
![](../img/redis/redis-05.jpg)


### 问题：(error) MOVED 8223 172.18.0.1:7001
* 背景：通过redis-cli -p 7002命令连接redis集群，设置值时报错
* 原因：连接命令缺少-c
* 解决：
    使用正确连接：redis-cli -c -p 7002
![](../img/redis/redis-04.jpg)


### 问题：Error response from daemon: OCI runtime create failed: container_linux.go
* 问题详情
```
docker: Error response from daemon: OCI runtime create failed: container_linux.go:349: starting container process caused "process_linux.go:449: container init caused \"write sysctl key net.core.somaxconn: open /proc/sys/net/core/somaxconn: no such file or directory\"": unknown.
```
* 背景：wsl2中使用命名空间隔离容器后，通过shell脚本创建redis集群时报错，容器创建成功，但是不能正常启动
* 解决：移除net.core.somaxconn的设置
```修改前
createRedisClusterContainer() {
  # 设置绿色字体echo -e "\033[32m 绿色字 \033[0m"
  echo -e "\033[32m Start to create redis cluster container \033[0m"
  # 注：此处redis.conf文件如果不存在，会默认创建名为redis.conf的文件夹
  for port in ${ports}; do
    docker run -d -it \
      -p "${port}":"${port}" \
      -p 1"${port}":1"${port}" \
      -v ${config_dir}/"${port}"/redis.conf:/usr/local/etc/redis/redis.conf \
      -v ${data_dir}/"${port}":/data \
      --name redis-"${port}" \
      --net ${net_name} \
      --sysctl net.core.somaxconn=1024 \
      redis:buster \
      redis-server /usr/local/etc/redis/redis.conf
  done
  echo -e "\033[32m Successfully created redis cluster container \033[0m"
}
```
```修改后
createRedisClusterContainer() {
  # 设置绿色字体echo -e "\033[32m 绿色字 \033[0m"
  echo -e "\033[32m Start to create redis cluster container \033[0m"
  # 注：此处redis.conf文件如果不存在，会默认创建名为redis.conf的文件夹
  for port in ${ports}; do
    docker run -d -it \
      -p "${port}":"${port}" \
      -p 1"${port}":1"${port}" \
      -v ${config_dir}/"${port}"/redis.conf:/usr/local/etc/redis/redis.conf \
      -v ${data_dir}/"${port}":/data \
      --name redis-"${port}" \
      --net ${net_name} \
      redis:buster \
      redis-server /usr/local/etc/redis/redis.conf
  done
  echo -e "\033[32m Successfully created redis cluster container \033[0m"
}
```


### 问题： Not all 16384 slots are covered by nodes
* 背景：创建redis集群，分配节点时报错


### 问题：Node zsx-2.local:7000 is not empty.
* 背景：创建redis集群，分配节点时报错
![](../img/redis/redis-02.jpg)
* 解决：
    * 1.redis-cli -p 7000
    * 2.flushall
    * 3.cluster reset
    * 4.exit


### 问题：OCI runtime exec failed: exec failed: container_linux.go:349: starting container process caused "exec: \"bash\": executable file not found in $PATH": unknown
* 背景：执行如下命令，进入容器内部报错
```
docker exec -it redis-container-id bash
```
* 原因：redis镜像是使用alpine制作
* 解决：修改命令
```
docker exec -it redis-container-id /bin/sh
```



## <h2 style="text-align: center;"> ------------------**SHELL**------------------ </h2>

### 问题：$'\r': 未找到命令
* 背景：win10下执行通过Cygwin64执行windows下编写的shell脚本
* 原因：window下的换行符多了‘\r’
* 解决：通过文本编辑工具Notepad++将文件格式转成Unix文件
![](../img/shell-1.jpg)



## <h2 style="text-align: center;"> ------------------**WSL**------------------ </h2>

### 问题：can't connect to the agent: IPC connect call failed
* 解决：将wsl1转换为wsl2


### 问题：Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?
* 背景：通过sudo apt install docker.io后，执行docker命令报错
* 解决：
    * 如果已安装docker容器，直接启动：sudo service docker start
    * 如果想重新安装
        * 1.卸载docker，安装参考[教程](https://blog.csdn.net/zsx18273117003/article/details/90707444)
        * 2.启动docker服务：sudo service docker start
```快速安装
sudo curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh
sudo service docker start
```

### 问题：problem running iptables
* 详情：
```
ERROR: problem running iptables: iptables v1.8.4 (legacy): can't initialize iptables table `filter': Table does not exist (do you need to insmod?)
Perhaps iptables or your kernel needs to be upgraded
```
* 背景：背景：win10下，ubuntu子系统查看防火墙状态报错
    * 命令：sudo ufw status
* 原因：ufw仅适用于具有Linux内核的系统


### 问题：sshd: no hostkeys available -- exiting.
* 背景：win10下，ubuntu子系统启动ssh服务报错
    * 命令：sudo service ssh start
* 解决：
    * 重新配制openssh-server软件包：sudo dpkg-reconfigure openssh-server


### 问题：System has not been booted with systemd as init system (PID 1). Can't operate. Failed to connect to bus: Host is down
* 背景：wsl2下ubuntu执行命令sudo systemctl restart docker报错
* 原因：到目前为止，在WSL 2中我们没有systemd
* 解决：使用命令sudo /etc/init.d/docker start | sudo /etc/init.d/docker restart | sudo service docker restart


### 问题：WSL 2 需要更新其内核组件
* 解决：下载更新程序并安装，进行更新
    * [下载地址](https://wslstorestorage.blob.core.windows.net/wslblob/wsl_update_x64.msi)



## <h2 style="text-align: center;"> ------------------**ZOOKEEPER**------------------ </h2>

### 问题：The Docker Engine you're using is running in swarm mode
* 背景：启动zookeeper集群时提示警告
* 解决，执行命令：docker swarm leave --force