#!/bin/bash

:<<EOF
多行注释EOF可以使用其他符号替换
下面是关于数组的操作
EOF
:<<!
多行注释,!替换EOF
!

# 定义数组
arr1=(a b c d)
# 读取数组
echo "${arr1[1]}"

arr2=(a 
b 
c 
d
)
echo "${arr2[2]}"

arr3[0]=Aa
arr3[1]=BbB
arr3[2]=CC
arr3[3]=DDD
echo ${arr3[0]}

# 获取数组中所有元素
echo "${arr3[@]}"

# 获取数组的长度
echo ${#arr3[@]}
echo ${#arr3[*]}

# 获取数组中单个元素的长度
echo ${#arr3[1]}