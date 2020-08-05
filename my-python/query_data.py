#!/usr/bin/python
# -*- coding: UTF-8 -*-
import string
import time

import psycopg2
import random


conn = psycopg2.connect(database="test", user="test", password="123456", host="192.168.1.110", port="5432")
print("Opened database successfully")

cur = conn.cursor()

for i in range(1, 40):
    num_query = random.randint(100, 5000)
    cur.execute(f'SELECT id, uid From master_data.md_data LIMIT {num_query}')
    rows = cur.fetchall()
    time.sleep(10)
    print(rows)
    print()
print("Records created successfully")

conn.close()
