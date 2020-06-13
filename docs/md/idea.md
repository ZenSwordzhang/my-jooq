### 拉取镜像
* docker pull crazymax/jetbrains-license-server
* docker run -d -p 8000:8000 --name jls -e TZ="UTC/GMT-7" -e JLS_VIRTUAL_HOSTS={org.sample.com} -v /usr/local/share/data/jetbrains:/data crazymax/jetbrains-license-server:latest

## 参考网站

### 如何在一台Ubuntu 18.04服务器上将Nginx配置为Web服务器和Apache的反向代理
[reverse-proxy](https://www.digitalocean.com/community/tutorials/how-to-configure-nginx-as-a-web-server-and-reverse-proxy-for-apache-on-one-ubuntu-18-04-server)










