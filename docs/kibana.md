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

### 获取索引映射信息
* GET /library/_mapping

### 获取集群内的所有映射信息
* GET /_all/_mapping

### 删除映射
* DELETE /library/_doc/_mapping
* DELETE /library/_doc/_mapping?pretty=true

## 基本查询

### 合并查询
```
GET /library/_mget
{
  "ids": ["1","2","3","4","5","6","7"]
}
```
### 简单查询
* 带索引：GET /library/_search?q=title:elasticsearch
* 不带索引：GET /_search?q=title:elasticsearch

## term查询

### 查询某个字段里有某个关键词的文档
```
GET /library/_search
{
  "query": {
    "term": {
      "preview": "es"
    }
  }
}
```

### 查询某个字段里有多个关键词的文档
* minimum_should_match指定匹配数量
* minimum_should_match值为1，表示只要匹配一个
* minimum_should_match值为2，表示2个都要匹配
```
GET /library/_search
{
  "query": {
    "bool": {
      "minimum_should_match": 1,
      "should": [
        {
          "term": {
            "preview": "es"
           }
        },
        {
          "term": {
            "preview": "php"
          }
        }
      ]
    }
  },
  "highlight": {
    "fields": {
      "preview": {}
    }
  }
}
```

### 控制查询返回的数量
```
GET /library/_search
{
  "from": 1,
  "size": 2,
  "query": {
    "term": {
      "title": "elasticsearch"
    }
  }
}
```
## match查询
* match跟term区别是,match查询的时候,elasticsearch会根据你给定的字段提供合适的分析器,而term查询不会有分析器分析过程

### match
```
GET /library/_search
{
  "query": {
    "match": {
      "preview": "es"
    }
  }
}
```

### match_phrase查询
* slop表示关键词之间间隔多少个未知单词
``` 
GET /library/_search
{
  "query": {
    "match_phrase": {
      "preview": {
        "query": "es,1",
        "slop": 1
      }
    }
  }
}
```

### multi_match查询
* 例：查询title或preview这两个字段里包含php关键词的文档
```
GET /library/_search
{
  "query": {
    "multi_match": {
      "query": "php",
      "fields": ["title","preview"]
    }
  }
}
```

### _source指定返回的字段
```
GET /library/_search
{
  "_source": ["preview","title"],
  "query": {
    "match": {
      "preview": "es"
    }
  }
}
```

### stored_fields
```
GET /library/_search
{
  "stored_fields": ["preview","title"],
  "query": {
    "match": {
      "preview": "es"
    }
  }
}
```

### 控制加载字段
* 不使用通配符
```
GET /library/_search?_source_includes=title,price&_source_excludes=preview
{
  "query": {
    "match_all": {}
  }
}
```
* 使用通配符
```
GET /library/_search?_source_includes=tit*,pr*&_source_excludes=pre*
{
  "query": {
    "match_all": {}
  }
}
```

### sort排序
* desc降序、asc升序
```
GET /library/_search
{
  "query": {
    "match_all": {}
  },
  "sort": [
    {
      "price.keyword": {
        "order": "desc"
      }
    }
  ]
}
```

### prefix前缀匹配查询
```
GET /library/_search
{
  "query": {
    "prefix": {
      "title": {
        "value": "p"
      }
    }
  }
}

```

### range控制范围
* nclude_lower: 是否包含范围的左边界,默认是true
* include_upper: 是否包含范围的右边界,默认是true
```
GET /library/_search
{
  "query": {
    "range": {
      "price": {
        "from": "10",
        "to": "20",
        "include_lower": true,
        "include_upper": false
      }
    }
  }
}
```

### wildcard通配符查询
``` 带*匹配
GET /library/_search
{
  "query": {
    "wildcard": {
      "preview": "es*"
    }
  }
}
```
``` 带?匹配
GET /library/_search
{
  "query": {
    "wildcard": {
      "preview.keyword": "e?-1"
    }
  }
}
```

### fuzzy模糊查询
```
GET /library/_search
{
  "query": {
    "fuzzy": {
      "preview": "php"
    }
  }
}
```
* fuzziness（可选，字符串）匹配允许的最大编辑距离
* max_expansions（可选，整数）创建的最大变体数。默认为50
* 避免在max_expansions参数中使用较高的值，尤其是当prefix_length参数值为时0
* max_expansions由于检查的变量数量过多，参数中的高值 可能导致性能不佳。
* prefix_length（可选，整数）创建扩展时保留不变的开始字符数，默认为0
* transpositions（可选，布尔值）指示编辑是否包括两个相邻字符的变位（ab→ba），默认为true。
* rewrite（可选，字符串）用于重写查询的方法
```
GET /library/_search
{
  "query": {
    "fuzzy": {
      "preview": {
        "value": "php",
        "fuzziness": "AUTO",
        "max_expansions": 50,
        "prefix_length": 0,
        "transpositions": true,
        "rewrite": "constant_score"
      }
    }
  }
}
```



## 参考文档
### 映射
* [removal-of-types](https://www.elastic.co/guide/en/elasticsearch/reference/7.7/removal-of-types.html)

### 查询
* (query-dsl)[https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl.html]














