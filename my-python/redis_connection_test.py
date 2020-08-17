#!/usr/bin/python3
# -*- coding: UTF-8 -*-
import time

import redis
import threading


class MyThread(threading.Thread):

    def __init__(self, thread_name):
        threading.Thread.__init__(self)
        self.thread_name = thread_name

    def run(self):
        py_redis = redis.Redis(host='localhost', port=6379, db=0, decode_responses=True)
        print(py_redis.time())
        print(py_redis.client())
        time.sleep(100)
        print(py_redis.time())
        print(py_redis.client())


# 创建新线程
for i in range(1, 20000):
    thread = MyThread(f'Thread-{i}')
    thread.start()
