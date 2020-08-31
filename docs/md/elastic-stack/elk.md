### 单机版启动

#### elasticsearch
* docker run --rm -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" --name elasticsearch -d elasticsearch:7.6.2
* docker run -d --rm -it -v /d/usr/local/etc/elasticsearch/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml --name elasticsearch -p 9200:9200 -p 9300:9300 elasticsearch:7.6.2

#### logstash
* docker run --rm -p 5044:5044 -p 9600:9600 --name logstash -d logstash:7.6.2
* docker run -it -v /d/usr/local/etc/logstash/config/logstash.yml:/usr/share/logstash/config/logstash.yml -v /d/usr/local/etc/logstash/pipeline:/usr/share/logstash/pipeline --name logstash -p 9600:9600 -p 5044:5044 logstash:7.6.2
#### kibana
docker run --rm -p 5601:5601 --name kibana --link elasticsearch -d kibana:7.6.2

#### 删除容器
* docker rm -f elasticsearch logstash kibana

#### logstash安装logstash-codec-json_lines插件
* docker exec -it logstash bash
* cd /bin
* logstash-plugin install logstash-codec-json_lines
* 退出容器: exit
* 重启logstash服务: docker restart logstash

#### 安装logstash-input-file插件
* logstash-plugin install logstash-input-file

#### 安装logstash-input-udp插件
* logstash-plugin install logstash-input-udp

### docker-compose构建ELK
```docker-compose-elk.yml
version: '3'
services:
  elasticsearch:
    image: elasticsearch:7.6.2
    container_name: elasticsearch
    environment:
        # 设置集群名称为elasticsearch
        cluster:
            name=elasticsearch
        # # 以单一节点模式启动
        discovery:
            type=single-node
        # 设置使用jvm内存大小
        ES_JAVA_OPTS: -Xms512m -Xmx512m
    volumes:
        # 插件文件挂载
        - /d/usr/local/opt/elasticsearch/plugins:/usr/share/elasticsearch/plugins
        # 数据文件挂载
        - /d/usr/local/var/elasticsearch/data:/usr/share/elasticsearch/data
        - /d/usr/local/etc/elasticsearch/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml
    ports:
      - 9200:9200
      - 9300:9300
  logstash:
    image: logstash:7.6.2
    container_name: logstash
    volumes:
      # 挂载logstash的配置文件
        - /d/usr/local/etc/logstash/config/logstash.yml:/usr/share/logstash/config/logstash.yml
        - /d/usr/local/etc/logstash/pipeline/logstash.conf:/usr/share/logstash/pipeline/logstash.conf
        - /d/usr/local/logs:/usr/share/logstash/logs
    depends_on:
        # logstash在elasticsearch启动之后再启动
        - elasticsearch
    links:
        # 可以用es这个域名访问elasticsearch服务
        - elasticsearch:es
    ports:
        - 9600:9600
        - 5044:5044
  kibana:
    image: kibana:7.6.2
    container_name: kibana
    links:
        - elasticsearch:es
    depends_on:
        - elasticsearch
    environment:
        - "elasticsearch.hosts=http://es:9200"
    ports:
        - 5601:5601  
  elasticsearch-head:
    image: mobz/elasticsearch-head:5
    container_name: elasticsearch-head
    ports:
        - 9100:9100
    volumes:
        # 原vendor.js文件地址：https://github.com/mobz/elasticsearch-head/blob/master/_site/vendor.js
        # 新的vendor.js为原vendor.js文件修改了6886行、7573行后的文件
        # contentType: "application/x-www-form-urlencoded改成了contentType: "application/json;charset=UTF-8"
        - /d/usr/local/etc/elasticsearch-head/_site/vendor.js:/usr/src/app/_site/vendor.js


```

## 补充插件
### logstash
* logstash-filter-multiline

## 参考链接

### 安全
* [配置 SSL、TLS 以及 HTTPS 来确保 Elasticsearch、Kibana、Beats 和 Logstash 的安全](https://www.elastic.co/cn/blog/configuring-ssl-tls-and-https-to-secure-elasticsearch-kibana-beats-and-logstash#preparations)
* [How To Secure Your Elastic Stack](https://blog.pythian.com/how-to-secure-your-elastic-stack-plus-kibana-logstash-and-beats/)

* [kibana-elasticsearch](https://www.elastic.co/guide/en/kibana/current/elasticsearch-mutual-tls.html)

### 第三方
* [ELKStack](http://docs.flycloud.me/docs/ELKStack/index.html)





