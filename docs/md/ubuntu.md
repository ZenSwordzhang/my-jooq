## 简写
* HDD(Hard Disk Driver)
* SSD(Solid State Disk)
* CPU(Central Processing Unit)
* LVM(Logical Volume Manager)
* NFS(Network File System)

## 命令

### 参数详解

#### htop命令
```console
 1[             28.8*] 4 [                 3.9%]   7[                        6.7%]  10 [                                  6.8%]
 2[              4.0%] 5 [                 7.7%]   8[                        4.6%]  11 [                                  6.7%]                                                                                    
 3[              9.3%] 6 [                 7.7%]   9[                        2.6%]  12 [                                  3.3%]
 Mem[                               11.5G/31.2G]   Tasks:52, 488 thr; 1 running
 Swp[                                4.5m/8.00G]   Load average: 6.17 6.48 0.76
                                                   Uptime: 4 days, 17:42:13

   PID USER      PRI  NI    VIRT    RES    SHR S  CPU%  MEM%     TIME+ COMMAND                                                                                                                                                      
  1108 postgres   20   0  248432  27264  25136 S   0.0   0.3   0:20.52 postgres   
503913 ubuntu     20   0   71.4G  15.5G  6512M S  99.9   49.7  5:16.41 usr/share/elasticsearch/jdk/bin/java -Xshare:auto
```

* CPU使用情况
```console
 1[             28.8*] 4 [                 3.9%]   7[                        6.7%]  10 [                                  6.8%]
 2[              4.0%] 5 [                 7.7%]   8[                        4.6%]  11 [                                  6.7%]                                                                                    
 3[              9.3%] 6 [                 7.7%]   9[                        2.6%]  12 [                                  3.3%]
```
* 内存使用情况
    * Mem[                               11.5G/31.2G] 
* 交换分区使用情况
    * Swp[                                4.5m/8.00G]
* Tasks为进程总数
    * Tasks:52
* 系统1分钟、5分钟、10分钟的平均负载情况
    * Load average: 6.17 6.48 0.76
* Uptime为系统运行的时间
    * Uptime: 4 days, 17:42:13
* 进程信息
    * PID USER      PRI  NI    VIRT    RES    SHR S  CPU%  MEM%     TIME+ COMMAND

#### top命令 
* [参考链接](https://www.jianshu.com/p/608c63268f52)

##### 1.命令参数
* -b 批处理
* -c 显示完整的命令
* -I 忽略失效过程
* -s 保密模式
* -S 累积模式
* -i<时间> 设置间隔时间
* -u<用户名> 指定用户名
* -p<进程号> 指定进程
* -n<次数> 循环显示的次数

##### 2.命令结果
* top -u postgres
    * 分为上下部分
        * 上面是系统统计信息
        * 下面是进程信息
```console
top - 14:55:40 up 9 days, 11:51,  1 user,  load average: 0.00, 0.03, 0.05
Tasks: 216 total,   1 running, 215 sleeping,   0 stopped,   0 zombie
%Cpu(s):  1.3 us,  0.8 sy,  0.0 ni, 97.8 id,  0.0 wa,  0.0 hi,  0.1 si,  0.0 st
MiB Mem :   7948.5 total,    223.4 free,   3399.0 used,   4326.2 buff/cache
MiB Swap:      0.0 total,      0.0 free,      0.0 used.   3272.8 avail Mem 

 PID USER      PR  NI    VIRT    RES    SHR S  %CPU  %MEM     TIME+ COMMAND                                                                                                                                                      
1108 postgres  20   0  248432  27264  25136 S   0.0   0.3   0:20.52 postgres                                                                                                                                                     
1116 postgres  20   0  248544   7844   5676 S   0.0   0.1   0:00.31 postgres                                                                                                                                                     
1117 postgres  20   0  248432   6092   3940 S   0.0   0.1   0:10.04 postgres                                                                                                                                                     
1118 postgres  20   0  248432  10064   7912 S   0.0   0.1   0:10.04 postgres                                                                                                                                                     
1119 postgres  20   0  248836   7000   4660 S   0.0   0.1   0:10.49 postgres                                                                                                                                                     
1120 postgres  20   0  103488   4488   2360 S   0.0   0.1   0:10.54 postgres                                                                                                                                                     
1121 postgres  20   0  248744   6788   4452 S   0.0   0.1   0:00.44 postgres  
```
##### 3.系统统计信息
* 第一行：任务队列信息，同 uptime 命令的执行结果
    * top - 14:55:40 up 9 days, 11:51,  1 user,  load average: 0.00, 0.03, 0.05
        * 14:55:40 （当前系统时间）
        * up 9 days（系统运行时间）
        * 1 user （当前登录用户数）
        * load average: 0.00, 0.03, 0.05 （系统的平均负载数，表示 1分钟、5分钟、15分钟到现在的平均数）
* 第二行：进程统计信息
    * Tasks: 216 total,   1 running, 215 sleeping,   0 stopped,   0 zombie
        * 216 total （系统当前总进程总数）
        * 1 running （正在运行的进程数）
        * 215 sleeping （睡眠进程数）
        * 0 stopped （停止进程数）
        * 0 zombie （僵尸进程数）
* 第三行：CPU 统计信息
    * %Cpu(s):  1.3 us,  0.8 sy,  0.0 ni, 97.8 id,  0.0 wa,  0.0 hi,  0.1 si,  0.0 st
        * 1.3 us （用户空间CPU占用率）
        * 0.8 sy （内核空间CPU占用率）
        * 0.0 ni （用户进程空间改变过优先级的进程CPU的占用率）
        * 97.8 idd （空闲CPU占有率）
        * 0.0 wa （等待输入输出的CPU时间百分比）
        * 0.0 hi （硬件中断请求）
        * 0.1 si （软件中断请求）
        * 0.0 st （分配给运行在其它虚拟机上的任务的实际 CPU时间）
* 第四行：内存状态
    * MiB Mem :   7948.5 total,    223.4 free,   3399.0 used,   4326.2 buff/cache
        * 7948.5 total （物理内存总量 7948.5M）
        * 223.4 free （空闲内存 223.4M）
        * 3399.0 used （已使用的内存 3399.0M）
        * 4326.2 buff/cache （内核缓存使用/缓冲交换区 4326.2M）
* 第五行 swap交换分区信息
    * MiB Swap:      0.0 total,      0.0 free,      0.0 used.   3272.8 avail Mem 
        * 0.0 total （交换分区总量 0）
        * 0.0 free （空闲交换分区 0）
        * 0.0 used （已使用交换分区内存 0）
        * 3272.8 avail Mem （可用内存 3272.8M）

##### 4.进程信息
*  PID USER      PR  NI    VIRT    RES    SHR S  %CPU  %MEM     TIME+ COMMAND 
    * PID （进程id）
    * USER （进程所有者的用户名）
    * PR （进程优先级）
    * NI （nice值。负值表示高优先级，正值表示低优先级）
    * VIRT （进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES）
    * RES （进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA）
    * SHR （共享内存大小，单位kb）
    * S （进程状态。D=不可中断的睡眠状态 R=运行 S=睡眠 T=跟踪/停止 Z=僵尸进程）
    * %CPU （上次更新到现在的CPU时间占用百分比）
    * %MEM （进程使用的物理内存百分比）
    * TIME+ （进程使用的CPU时间总计，单位1/100秒）
    * COMMAND （进程名称[命令名/命令行]）


### iostat命令
* [参考链接](https://www.man7.org/linux/man-pages/man1/iostat.1.html)
* 可以通过命令获取参数详情
    * man iostat

#### 命令格式
* iostat[参数][时间][次数]

#### 命令参数
* -C 显示CPU使用情况
* -d 显示磁盘使用情况
* -k 以 KB 为单位显示
* -m 以 M 为单位显示
* -N 显示磁盘阵列(LVM) 信息
* -n 显示NFS 使用情况
* -p[磁盘] 显示磁盘和分区的情况
* -t 显示终端和CPU的信息
* -x 显示详细信息
* -V 显示版本信息


#### CPU Utilization Report

##### 查看CPU命令
* iostat -c
```console
ubuntu@ubuntu-backup:~ » iostat -c                                                                                                                                                                                                        1 ↵
Linux 5.4.0-42-generic (ubuntu-backup) 	09/09/2020 	_x86_64_	(12 CPU)

avg-cpu:  %user   %nice %system %iowait  %steal   %idle
           6.48    0.00    0.83    0.25    0.00   92.44

```
* %user：Show the percentage of CPU utilization that occurred while executing at the user level (application).
* %nice：Show the percentage of CPU utilization that occurred while executing at the user level with nice priority.
* %system： Show the percentage of CPU utilization that occurred while executing at the system level (kernel).
* %iowait： Show the percentage of time that the CPU or CPUs were idle during which the system had an outstanding disk I/O request.
* %steal：Show the percentage of time spent in involuntary wait by the virtual CPU or CPUs while the hypervisor was servicing another virtual processor.
* %idle：Show the percentage of time that the CPU or CPUs were idle and the system did not have an outstanding disk I/O request.


#### Device Utilization Report

##### 查看磁盘命令
* iostat -d
```console
ubuntu@ubuntu-backup:~ » iostat -d
Linux 5.4.0-42-generic (ubuntu-backup) 	09/09/2020 	_x86_64_	(12 CPU)

Device             tps    kB_read/s    kB_wrtn/s    kB_dscd/s    kB_read    kB_wrtn    kB_dscd
loop0             0.00         0.00         0.00         0.00        739          0          0
loop1             0.00         0.00         0.00         0.00       2135          0          0
loop2             0.00         0.00         0.00         0.00       1464          0          0
loop3             0.00         0.00         0.00         0.00        739          0          0
loop4             0.00         0.00         0.00         0.00       1483          0          0
loop5             0.02         0.03         0.00         0.00      12799          0          0
loop6             0.00         0.00         0.00         0.00          4          0          0
sda             111.95        46.84      1785.77       698.22   22704936  865538056  338419840

```
* Device: Device: This column gives the device (or partition) name as listed in the /dev directory.
* tps: Indicate the number of transfers per second that wereissued to the device. 
    * A transfer is an I/O request to the device. 
    * Multiple logical requests can be combined into a single I/O request to the device. 
    * A transfer is of indeterminate size
* kB_read/s：Indicate the amount of data read from the deviceexpressed in a number of blocks per second.(KB/s)
    * Blocks are equivalent to sectors and therefore have a size of 512 bytes.
* kB_wrtn/s：Indicate the amount of data written to the device expressed in a number of blocks per second.(KB/s)
* kB_dscd/s：Indicate the amount of data discarded for the device expressed in a number of blocks per second.(KB/s)
* kB_read：The total number of blocks read.(KB)
* kB_wrtn：The total number of blocks written.(KB)
* kB_dscd：The total number of blocks discarded.(KB)


##### 查看磁盘详情命令
* iostat -dx
 ```console
 ubuntu@ubuntu-backup:~ » iostat -dx
 Linux 5.4.0-42-generic (ubuntu-backup) 	09/09/2020 	_x86_64_	(12 CPU)
 
 Device            r/s     rkB/s   rrqm/s  %rrqm r_await rareq-sz     w/s     wkB/s   wrqm/s  %wrqm w_await wareq-sz     d/s     dkB/s   drqm/s  %drqm d_await dareq-sz  aqu-sz  %util
 loop0            0.00      0.00     0.00   0.00    0.10    14.21    0.00      0.00     0.00   0.00    0.00     0.00    0.00      0.00     0.00   0.00    0.00     0.00    0.00   0.00
 loop1            0.00      0.00     0.00   0.00    0.25     1.47    0.00      0.00     0.00   0.00    0.00     0.00    0.00      0.00     0.00   0.00    0.00     0.00    0.00   0.00
 loop2            0.00      0.00     0.00   0.00    0.18    22.52    0.00      0.00     0.00   0.00    0.00     0.00    0.00      0.00     0.00   0.00    0.00     0.00    0.00   0.00
 loop3            0.00      0.00     0.00   0.00    0.17    14.22    0.00      0.00     0.00   0.00    0.00     0.00    0.00      0.00     0.00   0.00    0.00     0.00    0.00   0.00
 loop4            0.00      0.00     0.00   0.00    0.29    17.65    0.00      0.00     0.00   0.00    0.00     0.00    0.00      0.00     0.00   0.00    0.00     0.00    0.00   0.00
 loop5            0.02      0.03     0.00   0.00    0.21     1.06    0.00      0.00     0.00   0.00    0.00     0.00    0.00      0.00     0.00   0.00    0.00     0.00    0.00   0.00
 loop6            0.00      0.00     0.00   0.00    0.00     1.00    0.00      0.00     0.00   0.00    0.00     0.00    0.00      0.00     0.00   0.00    0.00     0.00    0.00   0.00
 sda              0.91     46.84     0.15  14.13    0.61    51.61  110.98   1785.72   159.71  59.00    0.95    16.09    0.06    698.16     0.00   0.00    0.58 12217.76    0.01   7.97
 
 ```
* Device: This column gives the device (or partition) name as listed in the /dev directory.
* r/s: The number (after merges) of read requests completed per second for the device.
* rkB/s: The number of sectors read from the device per second.(KB)
* rrqm/s: The number of read requests merged per second that were queued to the device.
* %rrqm: The percentage of read requests merged together before being sent to the device.
* r_await：The average time (in milliseconds) for read requests issued to the device to be served. 
    * This includes the time spent by the requests in queue and the time spent servicing them.
* rareq-sz：The average size (in kilobytes) of the read requests that were issued to the device.
* w/s：The number (after merges) of write requests completed per second for the device.
* wkB/s：The number of sectors written to the device per second.(KB)
* wrqm/s：The number of write requests merged per second that were queued to the device.
* %wrqm：The percentage of write requests merged together before being sent to the device.
* w_await：The average time (in milliseconds) for write requests issued to the device to be served. 
    * This includes the time spent by the requests in queue and the time spent servicing them.
* wareq-sz：The average size (in kilobytes) of the write requests that were issued to the device.
* d/s：The number (after merges) of discard requests completed per second for the device.
* dkB/s：The number of sectors discarded for the device per second(KB)
* drqm/s：The number of discard requests merged per second that were queued to the device.
* %drqm：The percentage of discard requests merged together before being sent to the device.
* d_await：The average time (in milliseconds) for discard requests issued to the device to be served. 
    * This includes the time spent by the requests in queue and the time spent servicing them.
* dareq-sz：The average size (in kilobytes) of the discard requests that were issued to the device.
* aqu-sz：The average queue length of the requests that were issued to the device.
    * Note: In previous versions, this field was known as avgqu-sz.
* %util: Percentage of elapsed time during which I/O requests were issued to the device (bandwidth utilization for the device). 
    * Device saturation occurs when this value is close to 100% for devices serving requests serially.
    * But for devices serving requests in parallel, such as RAID arrays and modern SSDs, this number does not reflect their performance limits.




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

### 查看硬盘
* df -hl

### 查看目录硬盘占用
* du -hs dir_name

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
* netstat -lntup | grep LISTEN

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

### 查看磁盘活动情况
* sudo iotop

### iostat命令
* iostat[参数][时间][次数]

#### 查看磁盘IO
* iostat
* iostat -d
* iostat -dx

#### 查看TPS和吞吐量
* iostat -d -k 1 10

#### 查看设备使用率、响应时间
* iostat -d -x -k 1 10

#### 查看CPU状态
* iostat -c 1 10

### 查看内核数
* grep 'model name' /proc/cpuinfo | wc -l

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
