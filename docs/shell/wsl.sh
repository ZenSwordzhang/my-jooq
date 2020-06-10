#!/bin/bash


sudo /etc/init.d/docker start

sudo service ssh start

sudo docker restart postgres

# 递归方式给新生成的日志文件设置权限
sudo chmod -R 777 /data/dmplus/facilities/log

sudo chmod 777 /etc/sysctl.conf
# 输出内容到文件
# sudo echo vm.max_map_count=262144 >> /etc/sysctl.conf
# 上面写法提示警告，替换为(-a表示追加)
echo 'export vm.max_map_count=262144' | tee -a /etc/sysctl.conf > /dev/null
# 恢复原权限
sudo chmod 644 /etc/sysctl.conf

# 重新加载配置
sudo sysctl --system


sudo docker restart elasticsearch logstash filebeat kibana





