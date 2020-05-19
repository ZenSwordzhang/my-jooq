#!/usr/bin/python
# -*- coding: UTF-8 -*-
import socket  # 网络通信 TCP，UDP

# SOCK_DGRAM表示UDP，SOCKET_STREAM表示TCP
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

while True:
    msg = input('>>: ').strip()
    if not msg:
        continue

    client.sendto(msg.encode('utf-8'), ('192.168.1.110', 5000))

    if msg == 'close':
        break
client.close()
