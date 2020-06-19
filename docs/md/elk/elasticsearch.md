## 命令操作

### 删除索引
* curl -X DELETE http://localhost:9200/{indexName}


### 查看集群详细信息
* http://localhost:9200/_cluster/stats

### 查看节点详细信息
* http://localhost:9200/_cat/nodes?v

### 查看节点
* http://localhost:9200/_cat/nodes

### 重命名索引
1.将原索引复制到新索引
```
POST /_reindex
{
  "source": {
    "index": "old_index"
  },
  "dest": {
    "index": "new_index"
  }
}
```
* 2.删除原索引：DELETE old_index










