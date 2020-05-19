#!/usr/bin/python
# -*- coding: UTF-8 -*-
import socket

# 基于网络的数据报协议 UDP
server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
# 绑定这个端口，接收这个端口的消息，参数是元组，括号不能少
server.bind(('192.168.1.110', 5000))

while True:
    # 1024 缓冲区。recvfrom可以得到发送方的消息和地址，recv只能得到消息
    msg, addr = server.recvfrom(1024)
    print(msg, addr)
    # 发送数据到指定的地址
    server.sendto(msg.upper(), addr)
