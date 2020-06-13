### 拉取镜像
* docker pull shadowsocks/shadowsocks-libev

### 命令启动
* docker run -e PASSWORD=1234 -p 8388:8388 -p 8388:8388/udp -e METHOD=chacha20 -d --name=shadowsocks --restart=always shadowsocks/shadowsocks-libev:latest

### 配置文件
```config.json
{
    "server":"127.0.0.1",
    "server_port":8388,
    "password":"1234",
    "timeout":"60",
    "method":"chacha20",
    "mode":"tcp_only",
    "no_delay": true,
    "nameserver":"8.8.8.8",
}
```

### docker-compose启动
```docker-compose
shadowsocks:
  image: shadowsocks/shadowsocks-libev:latest
  container_name: shadowsocks
  ports:
    - "8388:8388/tcp"
  environment:
    - PASSWORD=1234
    - METHOD=chacha20
  restart: always
```

