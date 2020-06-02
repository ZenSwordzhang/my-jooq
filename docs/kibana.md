## 问题

### 问题1：No cached mapping for this field / refresh of fields not working
* 解决：
    * 进入[kibana管理界面](http://localhost:5601/)
    * 选择Management-》Index Patterns-》从列表中选择index pattern-》点击refresh按钮
    * ![](kibana-01.jpg)


## kibana界面的Dev Tools控制台进行增删改查

### 获取索引信息
* GET /{index_1}, {index_2}/_settings
    * 例：GET /test-spring-errors-20200602/_settings
    * 例：GET /test-spring-errors-20200602, test-spring-logs-20200602/_settings

### 获取所有索引信息
* GET /_all/_settings

### 插入索引信息
* (/{index}/_doc/{id}, /{index}/_doc, or /{index}/_create/{id})
# 已弃用：POST /library/books/1
* POST /library/_doc
* POST /library/_doc/1
* POST /library/_create/1
```
POST /library/_doc/1
{
  "title": "Elasticsearch : the definitive guide",
  "name": {
    "first": "Vineeth",
    "last": "Mohan"
  },
  "publish_date": "2015-02-06",
  "price":"35.99"
}
```

### 查询索引信息
* 已启用：GET /library/books/1
* GET /library/_doc/1

### 更新索引信息
```
PUT /library/books/1
{
  "title": "Elasticsearch : the definitive guide",
  "name": {
    "first": "Vineeth",
    "last": "Mohan"
  },
  "publish_date": "2015-02-06",
  "price":"25.99"
}
```

### 更新单个字段
```
POST /library/books/1/_update
{
  "doc": {
    "price": 10
  }
}
```

### 删除索引信息
* 已弃用：DELETE /library/books/1
* DELETE /library/_doc/1

### 删除索引
* DELETE /library

### 合并请求
``` 已弃用，不建议在多个get请求中指定类型
GET /_mget
{
  "docs": [
      {
        "_index": "library",
        "_type": "_doc",
        "_id": 1
      },
      {
        "_index": "library",
        "_type": "_doc",
        "_id": 2
      },
      {
        "_index": "library",
        "_type": "_doc",
        "_id": 3
      }
    ]
}
```
```替换为
GET /_mget
{
  "docs": [
      {
        "_index": "library",
        "_type": "_doc",
        "_id": 1
      },
      {
        "_index": "library",
        "_type": "_doc",
        "_id": 2
      },
      {
        "_index": "library",
        "_type": "_doc",
        "_id": 3
      }
    ]
}
```

### 合并请求
```已弃用
GET /library/_doc/_mget
{
  "ids": ["1", "3"]
}
```
```替换为
GET /library/_mget
{
  "ids": ["1", "3"]
}
```

### 批量插入
```已弃用
POST /library/_doc/_bulk
{ "index": {"_id": 1}}
{ "title": "es-id-1","price":1}
{ "index": {"_id": 2}}
{ "title": "es-id-2","price":2}
{ "index": {"_id": 3}}
{ "title": "es-id-3","price":3}
```
```替换为
POST /library/_doc/_bulk
{ "index": {"_id": 1}}
{ "title": "es-id-1","price":1}
{ "index": {"_id": 2}}
{ "title": "es-id-2","price":2}
{ "index": {"_id": 3}}
{ "title": "es-id-3","price":3}
```

### 批量操作
```已弃用
POST /library/books/_bulk
{ "delete": {"_index":"library", "_type":"book", "_id":1}}
{ "create": {"_index":"music", "_type": "classical", "_id":1}}
{ "title": "Ave verum corpus"}
{ "index": {"_index": "music", "_type": "classical"}}
{ "title": "Litaniac de Venerabili Altaris Sacroments"}
{ "update": {"_index": "library", "_type": "books", "_id": "2"}}
{"doc":{"price":"18"}}
```
```替换为
POST /library/_bulk
{ "delete": {"_index":"library", "_type":"book", "_id":1}}
{ "create": {"_index":"music", "_type": "classical", "_id":1}}
{ "title": "Ave verum corpus"}
{ "index": {"_index": "music", "_type": "classical"}}
{ "title": "Litaniac de Venerabili Altaris Sacroments"}
{ "update": {"_index": "library", "_type": "books", "_id": "2"}}
{"doc":{"price":"18"}}
```

### 内部版本控制
* 1.获取if_seq_no和if_primary_term：GET /library/_doc/1
* 2.执行更新
```已弃用
POST /library/_doc/1/_update?if_seq_no=12&if_primary_term=1
{
  "doc": {
    "price": 15
  }
}
```
```替换为
POST /library/_update/1
{
  "doc": {
    "price": 25
  }
}

```

### 外部版本控制
* 1.查看version的值：GET /library/_doc/1
* 2.执行更新(注：100要大于原version值)
```
POST /library/_doc/1?version=100&version_type=external
{
  "doc": {
    "price": 20
  }
}
```

### 建立映射
```
POST /library/_doc
{
 "settings": {
   "number_of_shards": 5,
   "number_of_replicas": 1
 },
 "mappings": {
   "books": {
     "properties": {
       "title": {"type": "string"},
       "name": {"type":"string","index":"not_analyzed"},
       "publish_date": {"type":"date", "index":"not_analyzed"},
       "price": {"type":"double"},
       "number": {"type": "integer"}
     }
   }
 }
}
```














