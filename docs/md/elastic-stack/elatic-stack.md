### 新增环境变量
```
CERTS_DIR_ES=/usr/share/elasticsearch/config/certs
CERTS_DIR_KIBANA=/usr/share/kibana/config/certs
CERTS_DIR_LOGSTASH=/usr/share/logstash/config/certs
CERTS_DIR_FILEBEAT=/usr/share/filebeat/config/certs
CERTS_DIR_METRICBEAT=/usr/share/metricbeat/config/certs
ELASTIC_PASSWORD=123456
PWD=/data/operations
```

### 生成证书
* 1.切换到elasticsearch安装目录
* 2.执行证书生成命令
    * bin/elasticsearch-certutil cert ca --days 1095 --pem -out /certs/bundle.zip
* 3.解压证书压缩文件
    * unzip /certs/bundle.zip
* 4.切换到证书私钥目录
* 5.将证书转换成pkcs8格式
    * openssl pkcs8 -in instance.key -topk8 -nocrypt -out instance.pem

### 新增对elastic stack进行tsl/ssl的认证配置
* 1.es配置
```elasticsearch.yml
xpack.security.enabled: true
xpack.security.transport.ssl.enabled: true
xpack.security.transport.ssl.verification_mode: certificate
xpack.security.transport.ssl.certificate_authorities: ${CERTS_DIR_ES}/ca/ca.crt
xpack.security.transport.ssl.certificate: ${CERTS_DIR_ES}/instance/instance.crt
xpack.security.transport.ssl.key: ${CERTS_DIR_ES}/instance/instance.key
```
* 2.kibana配置
```kibana.yml
elasticsearch.username: "kibana"
elasticsearch.password: "123456"

# 配置ssl认证后，需使用https访问https://localhost:5601/
server.ssl.enabled: true
server.ssl.certificate: ${CERTS_DIR_KIBANA}/instance/instance.crt
server.ssl.key: ${CERTS_DIR_KIBANA}/instance/instance.key
# es配置了ssl认证后，需指定证书路径，此处指定用于 Elasticsearch 实例的 PEM 证书文件路径
elasticsearch.ssl.certificateAuthorities: ["${CERTS_DIR_KIBANA}/ca/ca.crt"]
```
* 3.logstash配置
```logstash.yml
monitoring.elasticsearch.username: "logstash_system"
monitoring.elasticsearch.password: "123456"
```
```logstash.conf
input {
    http {
        port => 8088
        codec => "json"
        add_field => {
            source => "http"
            from => "app"
        }
        # 自定义用户：拥有manage_index_templates、monitor、manage_ilm等权限
        user => "logstash_writer_user"
        password => "123456"
    }
    beats {
        port => 5044
        add_field => {
            source => "beats"
        }
        ssl => true
        # This key need to be in the PKCS8 format
        ssl_key => "${CERTS_DIR_LOGSTASH}/instance/instance.pem"
        ssl_certificate => "${CERTS_DIR_LOGSTASH}/instance/instance.crt"
        ssl_certificate_authorities => "${CERTS_DIR_LOGSTASH}/ca/ca.crt"
        ssl_verify_mode => "force_peer"
    }
    file {
        add_field => {
            source => "file"
            from => "spring"
        }
        path => ["/usr/share/logstash/logs/test.log"]
        sincedb_path => "NUL"
        start_position => "beginning"
    }
}

output {
    elasticsearch {
        hosts => ["http://es01:9200"]
        index => "%{[source]}-%{[from]}-%{+YYYYMMdd}"
        # 自定义用户：拥有manage_index_templates、monitor、manage_ilm等权限
        user => "logstash_writer_user"
        password => "123456"
        manage_template => false
    }
}

```
* 4.filebeat配置
```filebeat.yml
output.logstash:
  hosts: ["zsx-2.local:5044"]
  # The maximum number of events to bulk in a single Logstash request. The default is 2048.
  bulk_max_size: 1024
  ssl.certificate_authorities: ["${CERTS_DIR_FILEBEAT}/ca/ca.crt"]
  ssl.certificate: "${CERTS_DIR_FILEBEAT}/instance/instance.crt"
  ssl.key: "${CERTS_DIR_FILEBEAT}/instance/instance.key"
  ssl.verification_mode: none
```
* 5.metricbeat配置
```
output.logstash:
  hosts: ["zsx-2.local:5044"]
  ssl.certificate_authorities: ["${CERTS_DIR_METRICBEAT}/ca/ca.crt"]
  ssl.certificate: "${CERTS_DIR_METRICBEAT}/instance/instance.crt"
  ssl.key: "${CERTS_DIR_METRICBEAT}/instance/instance.key"
  ssl.verification_mode: none
```

### elastic stack启动配置
* 1.启动es容器
* 2.进入es容器内部，设置内置用户密码，该类用户在kibana.yml、logstash.yml中，以及登录kibana界面时有用到
    * docker exec -it es bash
    * bin/elasticsearch-setup-passwords interactive 
* 3.重启es容器
* 4.启动kibana容器
* 5.使用超级用户elastic登录kibana界面
* 6.在kibana管理界面中创建角色logstash_writer_role，赋予manage_index_templates、monitor、manage_ilm等权限
* 7.创建用户logstash_writer_user，赋予logstash_writer_role角色权限，该用户在logstash.conf中有用到
* 8.再依次启动logstash、filebeat、metricbeat
