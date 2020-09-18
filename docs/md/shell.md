## 参数

### 文件存在
* -a file exists.
* -b file exists and is a block special file.
* -c file exists and is a character special file.
* -d file exists and is a directory.
* -e file exists (just the same as -a).
* -f file exists and is a regular file.
* -g file exists and has its setgid(2) bit set.
* -G file exists and has the same group ID as this process.
* -k file exists and has its sticky bit set.
* -L file exists and is a symbolic link.
* -n string length is not zero.
* -o Named option is set on.
* -O file exists and is owned by the user ID of this process.
* -p file exists and is a first in, first out (FIFO) special file or named pipe.
* -r file exists and is readable by the current process.
* -s file exists and has a size greater than zero.
* -S file exists and is a socket.
* -t file descriptor number fildes is open and associated with a terminal device.
* -u file exists and has its setuid(2) bit set.
* -w file exists and is writable by the current process.
* -x file exists and is executable by the current process.
* -z string length is zero.

## 命令

### cut [选项] 文件
* -f 列号        （ --field 提取第几列 ）
* -d 分隔符    （ --delimiter 按照指定分隔符分割列，默认的字段分隔符为"TAB"；）

### 查看shell版本
* bash --version

### 以root用户执行shell脚本
* sudo -S ./docker-metricbeat-up.sh

### shell中切换用户执行命令
* su - root -c "mkdir /data/test123" 
* sudo -s mkdir /data/test123

## 参考链接

### 注意事项
* [编写可靠shell脚本的八个建议](https://segmentfault.com/a/1190000006900083)