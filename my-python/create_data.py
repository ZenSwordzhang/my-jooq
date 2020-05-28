#!/usr/bin/python
# -*- coding: UTF-8 -*-
import string

import psycopg2
import random

address_list = {'北京', '上海', '深圳', '广州', '长沙', '成都', '吉林'}

conn = psycopg2.connect(database="test", user="test", password="test", host="127.0.0.1", port="5433")
print("Opened database successfully")

cur = conn.cursor()
cur.execute('''CREATE TABLE COMPANY
                   (ID INT PRIMARY KEY     NOT NULL,
                   NAME           TEXT    NOT NULL,
                   AGE            INT     NOT NULL,
                   ADDRESS        CHAR(50),
                   SALARY         REAL);''')

print("Table created successfully")

conn.commit()

for i in range(1, 20000):
    # 生成5位随机字符串
    name = ''.join(random.sample(string.ascii_letters + string.digits, 5))
    age = random.randint(18, 80)
    address = random.sample(address_list, 1)[0]
    salary = random.randrange(5000, 100000)
    cur.execute(f"INSERT INTO COMPANY (ID, NAME, AGE, ADDRESS, SALARY) "
                f"VALUES ({++i}, '{name}', {age}, '{address}', {salary} )")


conn.commit()
print("Records created successfully")

conn.close()
