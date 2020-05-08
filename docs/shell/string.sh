#!/bin/bash

# 获取字符串长度
string="My name is zsx"
echo ${#string}

# 提取子字符串
echo ${string:0:7}

# 查找子字符串（注意反引号）
echo `expr index "$string" is`
