#!/usr/bin/bash

#
#下面是字体输出颜色及终端格式控制
#字体色范围：30-37
echo -e "\033[30m 黑色字 \033[0m"
echo -e "\033[31m 红色字 \033[0m"
echo -e "\033[32m 绿色字 \033[0m"
echo -e "\033[33m 黄色字 \033[0m"
echo -e "\033[34m 蓝色字 \033[0m"
echo -e "\033[35m 紫色字 \033[0m"
echo -e "\033[36m 天蓝字 \033[0m"
echo -e "\033[37m 白色字 \033[0m"
#字背景颜色范围：40-47
echo -e "\033[40;37m 黑底白字 \033[0m"
echo -e "\033[41;30m 红底黑字 \033[0m"
echo -e "\033[42;34m 绿底蓝字 \033[0m"
echo -e "\033[43;34m 黄底蓝字 \033[0m"
echo -e "\033[44;30m 蓝底黑字 \033[0m"
echo -e "\033[45;30m 紫底黑字 \033[0m"
echo -e "\033[46;30m 天蓝底黑字 \033[0m"
echo -e "\033[47;34m 白底蓝字 \033[0m"

#控制选项说明
#\033[0m 关闭所有属性
#\033[1m 设置高亮度
#\033[4m 下划线
echo -e "\033[4;31m 下划线红字 \033[0m"
#闪烁
echo -e "\033[5;34m 红字在闪烁 \033[0m"
#反影
echo -e "\033[8m 消隐 \033[0m "

#\033[30m-\033[37m 设置前景色
#\033[40m-\033[47m 设置背景色
#\033[nA光标上移n行
#\033[nB光标下移n行
echo -e "\033[4A 光标上移4行 \033[0m"
#\033[nC光标右移n行
#\033[nD光标左移n行
#\033[y;xH设置光标位置
#\033[2J清屏
#\033[K清除从光标到行尾的内容
echo -e "\033[K 清除光标到行尾的内容 \033[0m"
#\033[s 保存光标位置
#\033[u 恢复光标位置
#\033[?25| 隐藏光标
#\033[?25h 显示光标
echo -e "\033[?25l 隐藏光标 \033[0m"
echo -e "\033[?25h 显示光标 \033[0m"

#docker exec -it redis-7000 bash -c " ls -lh && exit"
#docker ps -a



#container_names=
## 声明一个单节点容器名，后续创建集群分配节点需要
#redis_single_container_name=
## 生成要检查的容器名，并拼接"|"
## 注意：此处的容器名createRedisClusterContainer()方法中生成的容器名要一致
#for port in $(seq 7000 7005); do
#  container_names=$container_names"|redis-${port}"
#  if test -z "$redis_single_container_name"; then
#    redis_single_container_name=redis-${port}
#  fi
#done
#echo "$redis_single_container_name"




#createClusterDistributionNodes() {
#  echo "Start creating a cluster"
#  host_ip="192.168.1.110"
#  host_list=
#  for port in $(seq 7000 7005); do
#  host_list="$host_list$host_ip:${port} "
#  done
#  # 模拟键盘输入，自动选择yes，yes 命令可以无限重复产生其后面的字符”yes”，head 命令选择重复输入的次数
##  redis_cli_order="yes yes | head -1 | redis-cli --cluster create 192.168.1.110:7000 192.168.1.110:7001 192.168.1.110:7002 192.168.1.110:7003 192.168.1.110:7004 192.168.1.110:7005 --cluster-replicas 1"
#  redis_cli_order="yes yes | head -1 | redis-cli --cluster create ${host_list} --cluster-replicas 1"
#  echo "$redis_cli_order"
#  # 在容器redis-7000外部执行执行容器内的命令，进行主从节点分配
#  docker exec -it redis-7000 bash -c "$redis_cli_order"
#  echo "Successfully created cluster"
#}

#createClusterDistributionNodes


#CMD ["redis-7000", "-g", "daemon off;"]
#
#redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 --cluster-replicas 1
#
#docker restart redis-7000 redis-7001 redis-7002 redis-7003 redis-7004 redis-7005
#docker rm -rf redis-7000 redis-7001 redis-7002 redis-7003 redis-7004 redis-7005
