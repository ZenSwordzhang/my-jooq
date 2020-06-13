#!/bin/bash

# 字符串可以用单引号，也可以用双引号，也可以不用引号
myName=zsx

# 单引号里的任何字符都会原样输出，单引号字符串中的变量是无效的
# 单引号字串中不能出现单独一个的单引号（对单引号使用转义符后也不行），但可成对出现，作为字符串拼接使用。
# shellcheck disable=SC2016
str0='this is a apostrophe: $myName'
echo "$str0"

# shellcheck disable=SC2016
str1='this is a \apostrophe\\: $myName'
echo "$str1"

# 双引号里可以有变量
str2="this is a double quotes: $myName"
echo "$str2"

# 双引号里可以出现转义字符
str3="Hello, my name is \"$myName\""
echo "$str3"