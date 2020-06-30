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