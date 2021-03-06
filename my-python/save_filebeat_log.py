#!/usr/bin/python
# -*- coding: UTF-8 -*-
import datetime
import threading

import os

# 递归级联创建的目录
path = "D:/usr/local/logs/filebeat"
if not os.path.exists(path):
    os.makedirs(path)
today = datetime.date.today()
# 方法1： 此方法文件夹不存在会报错：FileNotFoundError: [Errno 2] No such file or directory
with open(f'D:/usr/local/logs/filebeat/filebeat-{today}.log', mode='a+', encoding='utf-8') as f:
    now = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S.%f')[:-3]
    thread_id = threading.currentThread().native_id
    log_str = f'{now} DEBUG {thread_id} --- [scheduling-1] org.jooq.tools.LoggerListener            : ' \
              'Executing query          : select id from user'
    f.writelines(f'{log_str}\n')
    f.close()
    print('保存日志成功')
