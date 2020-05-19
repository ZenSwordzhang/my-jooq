#!/usr/bin/python
# -*- coding: UTF-8 -*-

import socket

ip_port = ('127.0.0.1', 5001)

# 创建套接字
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 绑定服务地址
server.bind(ip_port)
# 监听连接请求
server.listen(5)
print('启动socket服务，等待客户端连接...')
# 等待连接，此处自动阻塞
conn, address = server.accept()
while True:
    # 接收信息
    client_data = conn.recv(1024).decode()
    if client_data == "exit":
        conn.close()
        exit("通信结束")
    print("来自%s的客户端向你发来信息：%s" % (address, client_data))
    # 回馈信息给客户端
    conn.sendall('服务器已经收到你的信息'.encode())
