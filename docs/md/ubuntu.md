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

### 
* 修改组：groupmod -n new_name： 修改组名

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

### bash下环境变量设置和删除
* 设置：export 变量名=变量值
* 删除：unset 变量名

### csh下环境变量设置和删除
* 设置：setenv 变量名 变量值
* 删除：unsetenv 变量名


### 临时修改主机名(重启后恢复原主机名)
* hostname new_name

### 永久修改主机名
* 1.修改</etc/hostname | /etc/sysconfig/network>文件
* 2.（可选）修改/etc/hosts配置文件
* 3.重启ubuntu

### 非 root 用户想要使用 docker ，需要将该用户添加到 docker 用户组
* sudo usermod -aG docker user-name

### 查看端口占用
* netstat -ap | grep port

### 杀死进程
* kill -9 PID

### 查看服务状态
* ps -aux | grep ssh
* service service_name status

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




