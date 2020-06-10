#!/usr/bin/bash

container_names=
# 生成要检查的容器名
for port in $(seq 7000 7005); do
  container_names=$container_names"|redis-${port}"
done

echo "container_names: $container_names"

# 去掉字符串首位的"|"，得到grep命令的匹配部分
grep_part="${container_names:1}"
echo "grep_part: $grep_part"

## 拼接命令实现过滤，匹配需要的信息
docker ps -a | grep -E "$grep_part" | tee redis.txt >/dev/null
# 按2个以上空格进行全局替换
< redis.txt sed 's/[[:space:]]\{2,\}/=/g' > tmpfile && mv tmpfile redis-sed.txt
# 按"="好进行切分，获取匹配的容器名
container_names_list=$(cut -d "=" -f7 redis-sed.txt)
echo "$container_names_list already exists, delete or not? y/n:"
read -r deleted_str
if [ "$deleted_str" == "y" ]; then
  docker rm -f "$container_names_list[*]"
else
  echo "efg"
fi