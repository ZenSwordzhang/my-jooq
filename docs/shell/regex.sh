#!/usr/bin/env bash

# cat redis.txt | sed 's/[ ][ ]*/====/g'
# cat redis.txt | sed 's/[ ]\{2,\}/====/g'
# cat redis.txt | sed 's/[[:space:]]\{2,\}/====/g'
# 上面写法提示警告，用下面写法替换
< redis.txt sed 's/[[:space:]]\{2,\}/====/g'