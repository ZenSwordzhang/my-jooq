#!/bin/bash

#for i in `seq 7000 7005`;
#for i in {7000..7005}
#for((i=7000; i<7006; i++));
# lt <
# le <=
# gt >
# -ge >=
# -eq =
# 上面写法会提示警告，替换为
for i in $(seq 7000 7005)
do
  printf "%s\n" "${i}"
done
printf "\n"


for i in 7000 7001 7002 7003 7004 7005
do
  printf "%s\n" "${i}"
done
printf "\n"

i=7000
while [ $i -lt 7006 ]
do
  i=$((i + 1))
  printf "%s\n" "${i}"
done
printf "\n"

# shell声明一个数组
declare -a container_names

i=0
# 生成要检查的容器名
for port in $(seq 7000 7005);
do
  # container_names=("${container_names[*]}" "|redis-${port}")
  # 疑问：上面写法生成的数组为何长度是2不是6？为何第一个"|"前面有个空格
  container_names[i]="|redis-${port}"
  i+=1
done

# 查看数组的内容，
printf "container_names：%s\n" "${container_names[@]}"
printf "container_names：%s\n" "${container_names[*]}"

# 查看数组的长度
echo ${#container_names[@]}
echo ${#container_names[*]}
# 获取数组下标为0的元素
echo "${container_names[0]}"
echo "${container_names[1]}"

# 数组转换成字符串，疑问：每个元素后面为什么会带空格？
names_str="${container_names[*]}"
echo "names_str: $names_str"

# 去掉首位的"|"，
grep_space="${names_str:1}"
echo "grep_space: $grep_space"
# 去掉字符串中的空格
grep_str="${grep_space//' '/''}"

echo "grep_str: $grep_str"
grep_order="grep -E $grep_str"
echo "grep_order: $grep_order"
# 把命令拼接在一起
docker ps -a | $grep_order

# 或匹配 注：“|”两边的空格也是匹配内容
# docker ps -a | egrep "redis-7000|redis-7001|redis-7002|redis-7003|redis-7004|redis-7005"  # 已弃用
#docker ps -a | grep -E "redis-7000| redis-7001| redis-7002| redis-7003| redis-7004| redis-7005"
# 注意内容首尾的斜杠“/”
#docker ps -a | awk "/redis-7000| redis-7001| redis-7002| redis-7003| redis-7004| redis-7005/"