## 命令

### 系统升级
#### 1.检查系统上是否有被锁住版本的软件包
* sudo apt-mark showhold

#### 2.如果有on hold软件包，使用下面的命令解锁这些软件包:
* sudo apt-mark unhold package_name

#### 3.更新APT列表，并且升级所有的已安装软件包
* sudo apt update
* sudo apt upgrade

#### 4.如果内核被升级，重启机器
* sudo systemctl reboot

#### 5.对于所有已经安装的软件包执行一个主要版本升级：
* sudo apt full-upgrade
    * apt full-upgrade可能会移除一些不必要的软件包。

#### 6.移除任何被自动安装的，但是不再被任何包所依赖的软件包：
* sudo apt --purge autoremove

#### 7.安装update-manager-core软件包
* sudo apt install update-manager-core

#### 8.强制升级(-d表示强制升级)
* sudo do-release-upgrade -d
* do-release-upgrade是“update-manager-core”软件包的一部分

#### 9.查看Ubuntu版本
* lsb_release -a

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

### 移动、修改文件 | 文件夹名(重命名)
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

### 查看网关
* netstat -rn
* route -n

### 查看硬盘
* df -lh

### 查看ip
* ip addr
* ifconfig

### 查看ubuntu版本 | 查看系统版本
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

### 断点续传
* wget -c download_addr

### grep不区分大小写匹配
* grep -i match_content

### 强行设置某个用户所在组
* usermod -g 用户组 用户名

### 把某个用户改为 group(s) 
* usermod -G 用户组 用户名

### 把用户添加进入某个组(s）
* usermod -a -G 用户组 用户名

### 创建用户
* useradd username
    * 例1：useradd zhangsan
    * 例2：useradd list -g zhangsan
``` 参数
-g groupname: 指定用户的基本组(事先存在), 没指定则使用与用户名相同的组名；
-G group1,group2: 指定用户所属的附加组，要事先存在；
-c "comment": 注释信息；
-d HOME_DIR: 家目录；
-s SHELL: 指定用户默认的shell，可用的所有shell列表在/etc/shells中；
```

### 显示用户默认创建信息
* useradd -D

### 查看用户信息
* id username
* cat /etc/passwd | grep username

### 查看所有用户
* cat /etc/passwd

### 修改用户信息
* usermod username
```
-u UID: 修改用户id为新的；
-g GID: 修改用户基本组为新的;
-G group1, group2: 修改用户附加组，原来的被覆盖；
```

### 删除用户
* userdel username
```
-r: 删除用户时，一起删除家目录
```

### 创建组
* groupadd groupname

### 查看组信息
* cat /etc/group | grep groupname

### 查看用户所有的组信息
* groups

### 查看所有的组信息
* cat /etc/group

### 修改组
* groupmod -n new_name:old_name

### 删除组
* groupdel groupname

### 更改组成员
* groupmems -g groupname [action]
```
-a,--add username: 指定用户加入组；
-d,--delete username: 从组中删除用户；
-p,--purge :从组中清除所有成员；
-l,--list: 显示组成员列表;
groups查看当前用户所在全部组
```
### 查看环境变量
* env
* export

### 列出变量PATH的值
* echo $PATH

### 命名一个新的环境变量
* export VAR_NAME=value

### 添加路径到PATH末尾
export PATH=$PATH:/path/to/your/dir

### 添加路径到PATH开头
export PATH=/path/to/your/dir:$PATH

### 环境变量作用域
* 1.用于当前终端
    * export CLASS_PATH=./JAVA_HOME/lib:$JAVA_HOME/jre/lib
* 2.用于当前用户
    * 写入用户主目录下的.bashrc文件
    * vim ~/.bashrc
    * export CLASS_PATH=./JAVA_HOME/lib:$JAVA_HOME/jre/lib
    * source ~/.bashrc
* 3.用于所有用户
    * 修改profile文件
    * sudo vim /etc/profile
    * export CLASS_PATH=./JAVA_HOME/lib:$JAVA_HOME/jre/lib
    * source /etc/profile

### bash下设置环境变量
* 设置：export 变量名=变量值

### bash下删除环境变量
* 删除：unset 变量名

### csh下设置环境变量
* 设置：setenv 变量名 变量值

### csh下删除环境变量
* 删除：unsetenv 变量名


### 临时修改主机名(重启后恢复原主机名)
* hostname new_name

### 不重启机器的情况下修改主机名
* 查看当前主机名：hostnamectl
* 1.使用hostnamectl修改主机名
    * sudo hostnamectl set-hostname zsx
* 2.修改/etc/hosts配置文件
* 3.编辑/etc/cloud/cloud.cfg文件
    * 如果安装了cloud-init软件包，则还需要编辑cloud.cfg文件
        * 修改内容 preserve_hostname: true
    * 如果没有安装cloud-init软件包，无需进行编辑操作
        * 查看是否安装了cloud-init软件包
            * 执行 ls -lh /etc/cloud/cloud.cfg 命令
            * 结果显示 ls: cannot access '/etc/cloud/cloud.cfg': No such file or directory，表示没有安装安装了cloud-init软件包

### 非 root 用户想要使用 docker ，需要将该用户添加到 docker 用户组
* sudo usermod -aG docker user-name

### 查看端口占用
* netstat -ap | grep port

### 杀死进程
* kill -9 PID

### 查看服务状态
* ps -aux | grep ssh
* service service_name status

### 切换用户
* sudo -s
* su root

### 获取文件控制访问列表
* getfacl FileName
    
### 设置ACL权限
* setfacl -m u:USERNAME:MODE FileName
* setfacl -m g:GROUPNAME:MODE FileName
    
### 删除ACL权限|取消ACL权限
* setfacl -x u:USERNAME:MODE FileName
* setfacl -x g:GROUPNAME:MODE FileName

### 删除所有ACL权限
* setfacl -b FileName

### 解压文件 xxx.tar.xz
* 1.将 xxx.tar.xz解压成 xxx.tar
    * xz -d xxx.tar.xz
* 2.再用 tar xvf xxx.tar来解包
    * tar xvf xxx.tar

### 匹配多个值(或)
* docker ps -a | egrep "metricbeat|kibana" 
* docker ps -a | awk '/metricbeat|kibana/'

## 文件

### 用户信息所在文件
* /etc/passwd
* 格式：name:password:UID:GID:GECOS:directory:shell
    * 例：zsx:x:1000:1000:,,,:/home/zsx:/bin/bash
    * name：登录用户名
    * password：密码
    * UID：用户ID
    * GID：用户主组ID
    * GECOS：用户全名或注释，用命令chfn可以修改注释信息，用命令finger可以更加详细的查看修改和用户信息
    * directory：用户主目录
    * /bin/bash：用户使用shell，用chsh命令可以直接修改用户的shell

### 组信息所在文件
* /etc/group

### 用户密码信息所在文件
* /etc/shadow

### 群组密码信息所在文件
* /etc/gshadow

### 域名与ip的对应关系所在文件
* /etc/hosts

### 主机名所在文件
* /etc/hostname

### 删除文件所有内容
* ggdG
```
gg为跳转到文件首行
dG为删除光标所在行以及其下所有行的内容
d为删除，G为跳转到文件末尾行
```

### 删除掉每行前面的空格和Tab键 | 去掉每行以#开头的内容 | 删除空行
* cat postgres.conf | sed 's/^[ \t]*//g' | grep -v "^#" | grep -v '^\s*$'
* cat postgres.conf | sed 's/^[ \t]*//g' | grep -v "^#" | grep -vE "^[[:blank:]]*$"
* cat postgres.conf | sed 's/^[ \t]*//g' | grep -v "^#" | sed '/^\s*$/d'
* cat postgres.conf | sed 's/^[ \t]*//g' | grep -v "^#" | awk NF test.conf
* cat postgres.conf | sed 's/^[ \t]*//g' | grep -v "^#" | tr -s '\n'

### 从远程主机复制文件夹到本地指定目录(-r表示文件夹操作，-P指定远程主机端口，如果端口为默认端口22时可以省略)
* scp -r -P 2222 zsx@zsx-2.local:/etc/metricbeat/certs /etc/metricbeat

### 从远程主机复制文件到本地指定目录
* scp -P 2222 zsx@zsx-2.local:/home/zsx/metricbeat-7.7.0-amd64.deb /home/ubuntu

### 从远程主机复制文件到本地指定文件
* scp -P 2222 zsx@zsx-2.local:/home/zsx/install.sh /home/ubuntu/test_install.sh

### 从本地复制文件到远程主机指定目录
scp -r -P 2222 /home/ubuntu/certs  zsx@zsx-2.local:/home/zsx

### 从本地复制文件到远程主机指定目录
scp -P 2222 /home/ubuntu/postgres.conf  zsx@zsx-2.local:/home/zsx

### 从本地复制文件到远程主机指定文件
scp -P 2222 /home/ubuntu/postgres.conf  zsx@zsx-2.local:/home/zsx/postgres1.conf

### 通过ssh连接远程主机
* ssh -p 2222 zsx@zsx-2.local


### deb格式（Debian软件包格式）操作，此处以metricbeat为例

#### 安装服务
sudo dpkg -i metricbeat-7.7.0-amd64.deb

#### 停止服务
* systemctl stop metricbeat

#### 启动服务
* systemctl start metricbeat

#### 重启服务
*  systemctl restart metricbeat

#### 设置开机启动
* systemctl enable metricbeat

#### 查看已经安装的包
* sudo dpkg -l metricbeat

#### 删除安装包
* sudo dpkg -r metricbeat

#### 删除安装包和配置文件
* sudo dpkg -P metricbeat

## 参考链接

### dumpe2fs命令
* [dumpe2fs命令](https://man.linuxde.net/dumpe2fs)

### ACL命令
* [ACL](https://zhuanlan.zhihu.com/p/65974697)
* [ACL](https://cloud.tencent.com/developer/article/1361573)

### 修改主机名
* [change-hostname](https://linuxize.com/post/how-to-change-hostname-on-ubuntu-18-04/)
