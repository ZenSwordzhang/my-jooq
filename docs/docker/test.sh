#!/usr/bin/bash

container_names=
# 生成要检查的容器名
for port in $(seq 7000 7005);
do
  container_names=$container_names"|redis-${port}"
done

echo "container_names: $container_names"

# 去掉字符串首位的"|"，得到grep命令的匹配部分
grep_part="${container_names:1}"
echo "grep_part: $grep_part"

## 拼接命令实现过滤，匹配需要的信息
docker ps -a | grep -E "$grep_part"


