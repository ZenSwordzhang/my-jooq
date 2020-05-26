## 问题

### 问题1：max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
* 背景：ubuntu下elasticsearch容器启动后自动退出
* 解决：
    * 1.在/etc/sysctl.conf文件中添加vm.max_map_count=262144
    * 2.执行命令重新加载配置：sudo sysctl --system

## 命令操作

### 删除索引
* curl -X DELETE http://localhost:9200/{indexName}












