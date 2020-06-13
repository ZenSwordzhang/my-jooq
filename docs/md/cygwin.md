### win10下安装apt-cyg，地址链接：[apt-cyg](https://github.com/transcode-open/apt-cyg "apt-cyg")
* [地址2](https://github.com/kou1okada/apt-cyg)
* 查看版本：apt-cyg --version

### win10下安装wget，地址链接：[wget](https://eternallybored.org/misc/wget/ "wget")
* 查看版本：wget --version

### lynx地址链接：[lynx](https://lynx.invisible-island.net/release/ "lynx")
* 其他安装参考：[lynx介绍](http://blog.fpliu.com/it/software/lynx "lynx介绍")
* 注：自定义安装太麻烦，使用Cygwin安装
* 查看版本：lynx --version

### 安装vim
* apt-cyg install vim

### docker中安装vim
* apt-get update
* apt-get install vim

### 修改docker镜像源地址
* 修改配置文件(/etc/docker/daemon.json)
    * 网易：http://hub-mirror.c.163.com
    * ustc：http://docker.mirrors.ustc.edu.cn
* 直接设置：docker run hello-world --registry-mirror=https://docker.mirrors.ustc.edu.cn

### 查看设置的docker镜像是否生效（重启docker）
* docker info

### 安装 telnet
* telnet的包是 inetutils，直接安装inetutils即可

### cygwin下安装redis
* 1.安装gcc
    * 选择包：gcc-core
    * 选择包：gcc-g++
* 2.安装make
    * 选择包：make
* 3.查看是否安装成功
    * gcc -v
    * make -v
* 4.下载redis
    * wget http://download.redis.io/releases/redis-6.0.5.tar.gz
* 5.解压下载的文件
    * tar zxvf redis-6.0.5.tar.gz
* 6.移动文件到指定目录
    * mv redis-6.0.5 /usr/local/redis -iv
* 7.切换到redis目录
    * cd /usr/local/redis
* 8.安装redis
    * make && make install
* 9.配置文件所在位置
    * /usr/local/redis/redis.conf
* 10.验证是否安装成功
    * redis-cli --version
    * 可执行下文件会默认安装到/usr/local/bin目录






