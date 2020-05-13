### 单机版启动

#### elasticsearch
* docker run --rm -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" --name elasticsearch -d elasticsearch:7.6.2

#### logstash
* docker run --rm -p 5044:5044 -p 9600:9600 --name logstash -d logstash:7.6.2

#### kibana
docker run --rm -p 5601:5601 --name kibana --link elasticsearch -d kibana:7.6.2