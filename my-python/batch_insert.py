#!/usr/bin/python
# -*- coding: UTF-8 -*-
import string

import psycopg2
import random

from psycopg2 import extras

address_list = {'北京', '上海', '深圳', '广州', '长沙', '成都', '吉林', '邵阳', '九江', '长春', '拉萨'}

conn = psycopg2.connect(database="test", user="test", password="test", host="127.0.0.1", port="5433")
print("Opened database successfully")

cur = conn.cursor()

# values 后面直接%s
sql = '''INSERT INTO COMPANY (ID, NAME, AGE, ADDRESS, SALARY) 
         VALUES %s
    '''
datalist = []
for i in range(1, 10000000):
    # 生成6位随机字符串
    name = ''.join(random.sample(string.ascii_letters + string.digits, 8))
    age = random.randint(18, 80)
    address = random.sample(address_list, 1)[0]
    salary = random.randrange(5000, 100000)
    # 行数据是以元组的形式存放
    datalist.append((++i, name, age, address, salary))
extras.execute_values(cur, sql, datalist, page_size=20000)
conn.commit()

print("All records created successfully")

conn.close()
