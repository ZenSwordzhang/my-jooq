## 命令

### 给指定用户赋予某个文件夹的权限
* sudo chown -R 用户名 文件路径
* -R表示递归处理
* sudo chown -R ubuntu /usr/local/share/elkf

### 给指定用户用户组赋予某个文件夹的权限
* sudo chown -R 用户名:用户组 文件路径
* -R表示递归处理
* sudo chown -R ubuntu:ubuntu /usr/local/share/elkf

### 查看用户所在用户组
* groups 用户名
* 查看ubuntu用户所在用户组：groups ubuntu

### 递归创建文件夹
* mkdir -p directory1/dic2/dic3
* mkdir -pv directory1/dic2/dic3

### 移动、修改文件 | 文件夹名
* sudo mv <former_filename | former_folder_name> <new_filename | new_folder_name>

### 查看系统内核
* uname -a

### 查看cpu
* cat /proc/cpuinfo | grep model\ name

### 查看内存
* cat /proc/meminfo | grep MemTotal

### 查看显卡
* lspci | grep 'VGA'

### 查看声卡
* lspci | grep -i 'Audio'

### 查看网卡
* lspci | grep -i 'Network'

### 查看硬盘
* df -lh

### 查看ubuntu版本
* lsb_release -a
* cat /etc/issue

### 显示正在监听的服务端口
* netstat -lntup

### 使用nc(NetCat)命令发送消息
* echo "test nc command" | nc 192.168.1.110 8080

### 查找目录
* find / -name dir_name -type d

### 查找文件
* find / -name file_name

### Linux vi 命令?
* dd  --删除当前行
* ndd  --删除n行数据
* dG  --删除当前后之后的全部行

### 查看cpu使用情况
* 方式1：使用top命令
    * top
* 方式2：使用htop命令
    * sudo apt install htop
    * htop

### 安装上传工具
* sudo apt-get install lrzsz
* 下载到本地命令：sz
* 上传到Linux上命令：rz


