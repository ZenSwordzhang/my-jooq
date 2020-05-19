#!/usr/bin/python
# -*- coding: UTF-8 -*-
import socket  # 网络通信 TCP，UDP

# SOCK_DGRAM表示UDP，SOCKET_STREAM表示TCP
host = ('127.0.0.1', 5000)
client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client.connect(host)

while True:
    msg = input('>>: ').strip()
    if not msg:
        continue

    client.sendto(msg.encode('utf-8'), host)

    if msg == 'close':
        break
client.close()
