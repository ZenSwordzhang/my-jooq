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

### 删除索引
* DELETE posts
```
{
  "acknowledged" : true
}
```


### 创建索引结构
```
PUT twitter
{
  "settings" : {
    "number_of_shards" : 1,
    "number_of_replicas" : 0
  },
  "mappings" : {
    "properties" : {
      "message" : { "type" : "text" }
    }
  },
  "aliases" : {
    "twitter_alias" : {}
  }
}
```
```result
{
  "acknowledged" : true,
  "shards_acknowledged" : true,
  "index" : "twitter"
}
```


### 根据id删除索引内容
* DELETE posts/_doc/2
```result
{
  "_index" : "posts",
  "_type" : "_doc",
  "_id" : "2",
  "_version" : 2,
  "result" : "deleted",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 5,
  "_primary_term" : 1
}
```

### 创建索引
```command
PUT posts/_doc/1
{
  "user" : "zhangsan",
  "postDate" : "2020-06-30",
  "message" : "trying out Elasticsearch"
}
```
```result
{
  "_index" : "posts",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 0,
  "_primary_term" : 1
}

```


### 获取索引结构信息
* GET posts
```result
{
  "posts" : {
    "aliases" : { },
    "mappings" : {
      "properties" : {
        "message" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "postDate" : {
          "type" : "date"
        },
        "user" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        }
      }
    },
    "settings" : {
      "index" : {
        "creation_date" : "1593484585679",
        "number_of_shards" : "1",
        "number_of_replicas" : "1",
        "uuid" : "-Z-Sp41QSmOTU5VXV-RcAw",
        "version" : {
          "created" : "7070099"
        },
        "provided_name" : "posts"
      }
    }
  }
}

```

### 获取索引映射信息
* GET posts/_mapping
```result
{
  "posts" : {
    "mappings" : {
      "properties" : {
        "message" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "postDate" : {
          "type" : "date"
        },
        "user" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        }
      }
    }
  }
}

```

### 获取索引全部内容
* GET posts/_search
```result
{
  "took" : 0,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 1,
      "relation" : "eq"
    },
    "max_score" : 1.0,
    "hits" : [
      {
        "_index" : "posts",
        "_type" : "_doc",
        "_id" : "1",
        "_score" : 1.0,
        "_source" : {
          "user" : "zhangsan",
          "postDate" : "2020-06-30",
          "message" : "trying out Elasticsearch"
        }
      }
    ]
  }
}
```

### 根据id获取索引内容
* GET posts/_doc/1
```result
{
  "_index" : "posts",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 1,
  "_seq_no" : 0,
  "_primary_term" : 1,
  "found" : true,
  "_source" : {
    "user" : "zhangsan",
    "postDate" : "2020-06-30",
    "message" : "trying out Elasticsearch"
  }
}

```

### 根据id更新索引(修改索引)指定字段
```command
POST posts/_update/1
{
  "doc": {
    "user": "lisi"
  }
}
```
```result
{
  "_index" : "posts",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 2,
  "result" : "updated",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 1
}
```

### 全量更新索引
```command
PUT posts/_doc/1
{
  "user" : "lisi",
  "postDate" : "2020-06-30"
}
```
```result
{
  "_index" : "posts",
  "_type" : "_doc",
  "_id" : "1",
  "_version" : 3,
  "result" : "updated",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 2,
  "_primary_term" : 1
}
```
###
```result
```
###
```result
```
###
```result
```
###
```result
```
###
```result
```

## 集成

### springboot集成es
* [ESUtilsTest](../../../src/test/java/com/zsx/utils/ESUtilsTest.java)

## 参考链接

### springboot集成
* [java-rest-high-document](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-document-index.html)








