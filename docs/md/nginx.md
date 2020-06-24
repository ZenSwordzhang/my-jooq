## 命令

### 重新加载配置文件
* nginx -s reload

### 重启
* nginx -s reopen

### 停止
* nginx -s stop


## 配置

### 负载均衡
* 轮询（默认）
upstream item {
    server 192.168.1.110:8000;
    server 192.168.1.110:8001;
}
* 权重分配
upstream item {
    server 192.168.1.110:8000 weight=1;
    server 192.168.1.110:8001 weight=3;
}
* 哈希分配
upstream item {
    ip_hash;
    server 192.168.1.110:8000;
    server 192.168.1.110:8001;
}
* 最少连接分配
upstream item {
    least_conn;
    server 192.168.101.60:81;
    server 192.168.101.77:80;
}
* fair（第三方）
    * 按后端服务器的响应时间来分配请求，响应时间短的优先分配
upstream item {
    server 192.168.101.60:81;
    server 192.168.101.77:80;
    fair;
}

