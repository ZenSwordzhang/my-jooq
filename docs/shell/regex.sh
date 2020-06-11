#!/usr/bin/env bash

# cat redis.txt | sed 's/[ ][ ]*/====/g'
# cat redis.txt | sed 's/[ ]\{2,\}/====/g'
# cat redis.txt | sed 's/[[:space:]]\{2,\}/====/g'
# 上面写法提示警告，用下面写法替换
< redis.txt sed 's/[[:space:]]\{2,\}/====/g'

container_names_list="|redis-7000|redis-7001|redis-7002|redis-7003|redis-7004|redis-7005"
echo "${container_names_list//|/' '}"

remove_redis="docker rm -f""${container_names_list//|/' '}"
printf "%s" "$remove_redis"
$remove_redis
