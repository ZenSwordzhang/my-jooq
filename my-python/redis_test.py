#!/usr/bin/python3
# -*- coding: UTF-8 -*-
import time

import redis

# 使用单个连接
py_redis = redis.Redis(host='localhost', port=6379, db=0, decode_responses=True)
# 使用连接池
# pool = redis.ConnectionPool(host='localhost', port=6379, db=0, decode_responses=True)
# p_redis = redis.Redis(connection_pool=pool)

# 清空指定的数据库
py_redis.flushdb()
# 清空所有数据库
# p_redis.flushall()

# --------------- string ---------------

# 增
py_redis.set('name', 'zsx')

# 查
print(f'name: {py_redis["name"]}')
print(f'name: {py_redis.get("name")}')
print(f'name type: {type(py_redis.get("name"))}')

# 改
py_redis.set('name', 'zhaoyun')
print(f'name: {py_redis.get("name")}')
py_redis.setrange('name', 1, 'abc')
print(f'name: {py_redis.get("name")}')

# 修改key的value值并返回原值，如果存在则直接修改，不存在则直接新建
old_name = py_redis.getset('name', 'liubei')
print(f'Old name: {old_name}')
print(f'New name: {py_redis.get("name")}')

# 为指定的字符串追加值，若不存在则直接创建
py_redis.append('names', ' guanyu')
print(f'After appending name: {py_redis.get("names")}')

# INCR 自增
py_redis.set('num', 1)
print(f'Before increment num: {py_redis.get("num")}')
# 默认自增1
py_redis.incr('num')
# 指定自增步长
py_redis.incr('num', 5)
print(f'After increment num: {py_redis.get("num")}')

# 删
py_redis.delete('name')
print(f'After deleting name: {py_redis.get("name")}')

# 设置过期时间1s
py_redis.setex('number', 1, 20)
py_redis.psetex('weight', 1000, 125)
print(f'Before expiration number: {py_redis.get("number")}')
print(f'Before expiration weight: {py_redis.get("weight")}')
time.sleep(1.1)
print(f'After expiration number: {py_redis.get("number")}')
print(f'After expiration weight: {py_redis.get("weight")}')

# 设置持久化，如果key值已过期，返回False
print(f'The return value after persist is set: {py_redis.persist("weight")}')
print(f'After persist weight: {py_redis.get("weight")}')

# 批量设置值
py_redis.mset({'name': 'zhangsan', 'age': 18, 'gender': 'True'})
print()

# --------------- hash ---------------

py_redis.hset('person', 'name', 'lisi')
print(f'Name of person: {py_redis.hget("person", "name")}')

# 批量设置
py_redis.hmset('person', {'name': 'wangwu', 'age': 18, 'gender': 'True'})
print(f'Name of person: {py_redis.hget("person", "name")}')
print(f'person: {py_redis.hgetall("person")}')
print(f'person type: {type(py_redis.hgetall("person"))}')

# 获取person中所有的key
print(f'All keys in person: {py_redis.hkeys("person")}')

# 判断key是否存在
print(f'Exists name in Person? : {py_redis.hexists("person", "name")}')

# 获取键值对个数
print(f'key-value number : {py_redis.hlen("person")}')

# 获取多个value值
print(f'values in person: {py_redis.hmget("person", "name", "age", "height")}')

# 获取所有values值
print(f'All values in person: {py_redis.hvals("person")}')

print()

# --------------- list ---------------

# 多次执行会多次插入，lpush：从左边插入
py_redis.lpush('animals', 'dog', 'cat', 'bear', 'penguin')
print(f'animals: {py_redis.lrange("animals", 0, -1)}')

# 在cat前插入
py_redis.linsert('animals', 'before', 'cat', 'cattle')
py_redis.linsert('animals', 'after', 'cat', 'fish')
print(f'animals: {py_redis.lrange("animals", 0, -1)}')

# 取出元素
py_redis.lpop('animals')
print(f'animals: {py_redis.lrange("animals", 0, -1)}')
# 删除元素，指定要删除的元素以及个数
py_redis.rpush('animals', 'dog', 'chicken', 'dog')
print(f'Before removal animals: {py_redis.lrange("animals", 0, -1)}')
py_redis.lrem('animals', 2, 'dog')
print(f'After removal animals: {py_redis.lrange("animals", 0, -1)}')

# 根据列表下标获取列表元素值
print(f'Element with index 1 in Animals : {py_redis.lindex("animals", 1)}')
print()

# --------------- set ---------------

py_redis.sadd('language', 'Arabic')

# 批量添加
py_redis.sadd('language', 'English', 'Chinese', 'Japanese', 'French', 'Russian')
py_redis.sadd('course', 'English', 'Chinese', 'math')

# 获取set集合中元素个数
print(f'language number: {py_redis.scard("language")}')
print(f'language: {py_redis.smembers("language")}')

# 判断元素是否在集合当中
print(f'Exists language? : {py_redis.sismember("language", "Chinese")}')

# 返回给定多个集合对象的差集，从左到右计算集合差集
print(f'Difference of language and course: {py_redis.sdiff("language", "course")}')

# 返回给定给定多个集合对象的差集并存储在目标（dest）集合中
py_redis.sdiffstore('diff_dest', 'language', 'course')
print(f'diff_dest: {py_redis.smembers("diff_dest")}')

# 返回给定集合的交集
print(f'The intersection of language and course: {py_redis.sinter("language", "course")}')

# 返回所有给定集合的并集
print(f'Union of language and course: {py_redis.sunion("language", "course")}')

# 将元素value从集合src移动到 集合 dest
# 若元素value在集合src中不存在，则集合dest中不会添加元素value
py_redis.smove('language', 'smove_dest', "Russian")
print(f'smove_dest: {py_redis.smembers("smove_dest")}')

# 随机取出元素
print(f'Before taking out the elements, language: {py_redis.smembers("language")}')
print(f'Take out the elements : {py_redis.spop("language")}')
print(f'Take out the elements : {py_redis.spop("language", 2)}')
print(f'After taking out the elements, language: {py_redis.smembers("language")}')

# 移除元素
print(f'Before removing the element, diff_dest: {py_redis.smembers("diff_dest")}')
print(f'Remove the number of elements: {py_redis.srem("diff_dest", "Russian")}')
print(f'After removing the element, diff_dest: {py_redis.smembers("diff_dest")}')

print()

# --------------- zset ---------------

# 根据scores值从小到大排列
py_redis.zadd('staff', {'zhangsan': 10, "lisi": 25, 'wangwu': 13, 'zhaoliu': 20, 'qianqi': 6})

# 查看所有元素
print(f'{py_redis.zrange("staff", 0, -1)}')
print(f'{py_redis.zrange("staff", 0, -1, withscores=True)}')

# 获取索引
print(f'index: {py_redis.zrank("staff", "lisi")}')

print(f'The return value after deletion: {py_redis.zrem("staff", "lisi")}')
print(f'After deleting, staff: {py_redis.zrange("staff", 0, -1)}')

# --------------- all ---------------

# 查看所有key
print()
print(f'keys: {py_redis.keys()}')

py_redis.close()
