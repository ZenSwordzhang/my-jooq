## 命令

### 查看模块列表及状态
* metricbeat modules list

### 启用模块
* metricbeat modules enable apache mysql

### 禁用模块
* metricbeat modules disable http

### 设置仪表盘模板
* metricbeat setup -e

### 设置Kibana仪表板
* docker run --net="host" docker.elastic.co/beats/metricbeat:7.7.1 setup --dashboards

### 验证Elasticsearch中是否存在服务器的统计信息
* curl -XGET 'http://localhost:9200/metricbeat-*/_search?pretty'

### 下载metricbeat.docker.yml文件
* curl -L -O https://raw.githubusercontent.com/elastic/beats/7.7/deploy/docker/metricbeat.docker.yml

### 验证Elasticsearch中是否存在服务器的统计信息
* curl -XGET http://localhost:9200/metricbeat-*/_search?pretty

### win10下powershell启动服务（命令关键字部分忽略大小写）
* Start-Service metricbeat
* start-service metricbeat

### win10下powershell查看服务
* get-service | findstr metricbeat

### win10下powershell停止服务
* stop-service metricbeat

### win10下powershell重启服务
* restart-service metricbeat

### win10下启动metricbeat
* metricbeat.exe -e -c metricbeat.yml


## 参考链接

### 使用
* [官网使用教程](https://www.elastic.co/guide/en/beats/metricbeat/current/metricbeat-getting-started.html)

### docker运行metricbeat
* [running-on-docker](https://www.elastic.co/guide/en/beats/metricbeat/current/running-on-docker.html)

### metricbeat.yml文件配置
* [configuration-metricbeat](https://www.elastic.co/guide/en/beats/metricbeat/current/configuration-metricbeat.html)
* [modules部分](https://www.elastic.co/guide/en/beats/metricbeat/current/metricbeat-modules.html)

### metricbeat.docker.yml文件下载地址
* [metricbeat.docker.yml](https://raw.githubusercontent.com/elastic/beats/7.7/deploy/docker/metricbeat.docker.yml)

### 输出到logstash
* [logstash-output](https://www.elastic.co/guide/en/beats/metricbeat/current/logstash-output.html)

### 添加属性
* [add-fields](https://www.elastic.co/guide/en/beats/metricbeat/current/add-fields.html)

### metricbeat部署及监控linux系统指标汇总
* [linux-metricbeat](https://owelinux.github.io/2018/07/30/article10-linux-metricbeat-diskio/)