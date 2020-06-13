##前言
[](#底部)


# 命令

## PowerShell命令（以管理员身份运行）
* [wsl](../img/wsl/wsl-01.jpg)
* [wsl1与wsl2比较](../img/wsl/wsl-02.jpg)

### win10下wsl1转换为wsl2操作
#### 1.要求
* 运行 Windows 10（已更新到版本 2004 的内部版本 19041 或更高版本）
    * 查看版本：winver
    * cmd命令查看版本：ver
* 必须启用“虚拟机平台”可选功能

#### 2.查看已安装的子系统
* wsl -l

#### 3.转换系统wsl1到wsl2
* wsl --set-version <distribution name> <versionNumber>
    * 例： wsl --set-version Ubuntu-20.04 2

#### 4.验证是否转换完成
* wsl -l -v

### 设置WSL2为默认体系结构
* wsl --set-default-version 2

### windows开机默认启动Ubuntu和ssh
* wsl -d Ubuntu-20.04 -u root /etc/init.d/ssh start

### 启用虚拟机平台可选组件
* Enable-WindowsOptionalFeature -Online -FeatureName VirtualMachinePlatform
* dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart

### 启用windows的子系统Linux
* 方式1：Enable-WindowsOptionalFeature -Online -FeatureName Microsoft-Windows-Subsystem-Linux
* 方式2：dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart

### 重启子系统
* net stop LxssManager
* net start LxssManager

## ubuntu命令

### 查看服务状态
* service ssh status

### 启动ssh服务
* sudo /etc/init.d/ssh start
* sudo service ssh start
* wsl -d Ubuntu-20.04 -u root /etc/init.d/ssh start(PowerShell启动)

### 查看端口是否连通
* 方式1：telnet IP PORT
    * 例： telnet baidu.com 80
* 方式2：ssh -v -p port username@ip
    * 例：ssh -v -p 2222 zsx@192.168.1.110
* 方式3：wget ip:port
    * 例：wget 192.168.1.110:2222

### 查看宿主机所在ip
* cat /etc/resolv.conf | grep nameserver | awk '{ print $2 }'

### 修改ubuntu镜像源
* 备份：sudo cp /etc/apt/sources.list /etc/apt/sources.list.bak
* 修改镜像文件：sudo vim /etc/apt/sources.list
```阿里镜像源sources.list
deb http://mirrors.aliyun.com/ubuntu/ focal main restricted universe multiverse
# 源镜像
deb-src http://mirrors.aliyun.com/ubuntu/ focal main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ focal-security main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal-security main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ focal-updates main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal-updates main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ focal-proposed main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal-proposed main restricted universe multiverse
# 预发布软件源，不建议启用
# deb http://mirrors.aliyun.com/ubuntu/ focal-backports main restricted universe multiverse
# deb-src http://mirrors.aliyun.com/ubuntu/ focal-backports main restricted universe multiverse
```
* 刷新软件源信息：sudo apt update
* 更新系统所有软件：sudo apt upgrade

### ubuntu20.04LTS下修改docker镜像源
* 1.创建目录：sudo mkdir -p /etc/docker
* 2.往文件daemon.json中写入内容
```
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://**.mirror.aliyuncs.com"]
}
EOF
```
3. 重新加载守护进程：sudo systemctl daemon-reload
4. 重启容器：sudo systemctl restart docker | sudo service docker restart | sudo /etc/init.d/docker restart
* 查看[阿里云专用镜像加速地址](https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors)

### 设置docker为守护进程
* sudo systemctl enable docker

### wsl2设置静态ip（在ubuntu中进行）
* 1.sudo apt install -y avahi-daemon
* 2.sudo /etc/init.d/dbus start
* 3.sudo /etc/init.d/avahi-daemon start
```
<PCNAME>.local解析为Windows机器，
<PCNAME>-2.local解析为ubuntu机器
```
* 另外还可以修改/etc/avahi/avahi-daemon.conf文件，覆盖Linux计算机的已发布主机名
```
[server]
host-name=WSL
因此WSL.local现在将解析为wsl机器
这将适用于在本地LAN上任何启用了mDNS的计算机上运行的任何查找
```


## 文件

### ip所在文件
* /etc/resolv.conf


## 参考链接

### wsl2开机启动配置
* [wsl2启动配置](https://blog.csdn.net/hiqiming/article/details/105244147)

### win10子系统安装(WSL)
* [win10子系统安装官网](https://docs.microsoft.com/zh-cn/windows/wsl/install-win10)
* [win10子系统安装](https://windows10.pro/bash-on-ubuntu-on-windows/)

### Windows Subsystem for Linux
* [Windows Subsystem for Linux](https://docs.microsoft.com/zh-cn/archive/blogs/wsl/)

### commandline
* [commandline](https://devblogs.microsoft.com/commandline/)

### SSH登录
[SSH登录](https://blog.csdn.net/zhouzme/article/details/81087837)

### 在Windows的Bash上运行Docker容器
* [running-docker-on-bash-on-windows](https://blog.jayway.com/2017/04/19/running-docker-on-bash-on-windows/)

### win10下wsl1转换为wsl2
* [win10下wsl1转换为wsl2](https://www.hotbak.net/key/WSL2%E5%AE%89%E8%A3%85%E8%AF%B4%E6%98%8EWSL%E5%8D%87%E7%BA%A7WSL2%E7%BD%91%E6%98%93%E8%AE%A2%E9%98%85.html)

### docker 安装
* [安装Docker CE](https://blog.csdn.net/zsx18273117003/article/details/90707444)

### ubuntu常用命令
* [ubuntu常用命令](https://blog.csdn.net/zsx18273117003/article/details/90731538)

### wsl2静态ip设置
* [ip设置](https://github.com/shayne/go-wsl2-host)
* [WSL2设置静态IP](https://github.com/microsoft/WSL/issues/4210)


[](#前言)
## 底部