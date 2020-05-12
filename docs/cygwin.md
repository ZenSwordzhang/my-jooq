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