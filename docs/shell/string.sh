#!/bin/bash

# 获取字符串长度
introduction="My name is zsx"
echo ${#introduction}

# 提取子字符串
echo "${introduction:0:7}"

# 查找子字符串（注意反引号）"is"在$introduction中的索引，已过时，不推荐使用expr
#echo `expr index "$introduction" is`
# 上面写法提示警告，替换为
# 符合则返回指定的正值，不符合则返回0
echo "$introduction" | awk '{printf("%d\n", match($0, "is"))}'