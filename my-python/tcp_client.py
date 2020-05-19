#!/usr/bin/python
# -*- coding: UTF-8 -*-

import socket

ip_port = ('127.0.0.1', 5001)

client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

client.connect(ip_port)

while True:
    msg = input("请输入要发送的信息： ").strip()
    if not msg:
        continue
    client.sendall(msg.encode())

    if msg == "exit":  # 如果输入的是‘exit’，表示断开连接
        print("结束通信")
        break

    server_reply = client.recv(1024).decode()
    print(server_reply)

client.close()
