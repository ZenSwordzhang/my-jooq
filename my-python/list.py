#!/usr/bin/python
# -*- coding: UTF-8 -*-

users = [{'id': 1, 'name': 'zhangsan', 'age': 20},
         {'id': 2, 'name': 'lisi', 'age': 18},
         {'id': 3, 'name': 'wangwu', 'age': 22}]
users.remove({'id': 1, 'name': 'zhangsan', 'age': 20})
user_2 = list(filter(lambda u: u['id'] == int('2'), users)).pop()
print(user_2)
# 删除元素
users.remove(user_2)
user_3 = list(filter(lambda u: u['id'] == int('3'), users)).pop()
print(user_3)
name = 'zhaoyun'
age = 16
if name:
    user_3['name'] = name
if age:
    user_3['age'] = age
print(user_3)
users.append(user_2)
users.append(user_2)
users.insert(1, user_2)


print('==============================')
print(users)




