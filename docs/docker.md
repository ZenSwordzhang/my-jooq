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

