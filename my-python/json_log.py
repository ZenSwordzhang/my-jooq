#!/usr/bin/python
# -*- coding: UTF-8 -*-
import datetime
import json
import random
import time

import requests

headers = {
    'Content-Type': 'application/json',
}

info_list = {'User error': 'ERROR', 'Password ERROR': 'ERROR', 'Login success': 'INFO',
             'Update success': 'INFO', 'Deletion success': 'INFO', 'Password not null': 'WARN',
             'Username not null': 'WARN'}

user_list = {1: 'zhangsan', 2: 'lisi', 3: 'wangwu', 4: 'zhaoliu', 5: 'qianqi'}

app_list = ['bulter', 'master-data', 'permission-manager']

path_list = ['/bulter', '/data', '/permission']

method_list = ['select', 'update', 'create', 'delete']

m = 0
# 模拟发送请求，生成日志数据
for i in range(1, 1000):
    now = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S.%f')[:-3]

    time_log = random.randrange(10, 35)

    # 模拟同一时间生成多条日志信息
    for j in range(1, time_log):
        msg = random.sample(info_list.keys(), 1)[0]
        log_lever = info_list[msg]
        user_id = random.sample(user_list.keys(), 1)[0]
        user_name = user_list[user_id]
        app_name = random.sample(app_list, 1)[0]
        path = random.sample(path_list, 1)[0]
        method = random.sample(method_list, 1)[0]
        data = {'env': 'test', 'timestamp': now, 'logLevel': log_lever, 'source': 'request', 'type': 'metrics',
                'message': msg, 'app': app_name, 'path': path, 'method': method, 'userId': user_id,
                'userName': user_name, 'duration': random.randrange(300, 550)}
        # 使用zsx-2.local会报socket.gaierror: [Errno 11001] getaddrinfo failed异常
        # response = requests.post('http://zsx-2.local:9601/', headers=headers, data=json.dumps(data))
        response = requests.post('http://127.0.0.1:9601/', headers=headers, data=json.dumps(data))
        m += 1
        print(m)

    time.sleep(random.randrange(1, 5))
