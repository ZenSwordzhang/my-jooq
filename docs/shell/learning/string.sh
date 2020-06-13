#!/bin/bash

port=1000
echo 1${port}

printf 1%d\\n ${port}
printf "Hello, %s\n" "$port"

# 获取字符串长度
introduction="My name is zsx"
echo ${#introduction}

# 提取子字符串
echo "${introduction:3}"
echo "${introduction:0:7}"

# 查找子字符串（注意反引号）"is"在$introduction中的索引，已过时，不推荐使用expr
#echo `expr index "$introduction" is`
# 上面写法提示警告，替换为
# 符合则返回指定的正值，不符合则返回0
echo "$introduction" | awk '{printf("%d\n", match($0, "is"))}'

# 全局替换字符串
echo abcdefgabCDEFGaBcde | sed "s/ab/12/g"

:<<!
# 正则匹配所有的数字
-P参数表明要应用正则表达式
-o表示只输出匹配的字符串
!
echo window10Ubuntu20.04 | grep -P '\d+' -o

:<<!
# 正则匹配当前目录下，以.sh结尾，且包含字符串"zhengze"的文件，找到多少次显示多少次
-xargs会将find结果作为grep的输入，防止find结果过多无法处理
!
#find . -name "*.sh" | xargs grep -P 'zhengze' -o
# 上面写法提示警告，可用下面两种写法的其中一种替换
# find . -name "*.sh" -print0 | xargs -0 grep -P 'zhengze' -o
find . -name "*.sh" -exec grep -P 'zhengze' -o {} +

# 替换语法：${变量/查找/替换值}
# 替换查找到的第一个元素：,替换成' '
a=11,222,345
b="${a/,/' '}"
# 全局替换
c="${a//,/' '}"
echo $a $b $c

# 字符串拼接
name="zhangsan"
gender="true"
info=$name$gender
printf "%s" $info