## 问题
### 问题1：has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
* 背景：elasticsearch无法连接到http://localhost:9200
* 解决：elasticsearch的配置文件elasticsearch.yml中新增配置
```
# elasticsearch-head插件访问需要配置
http.cors.enabled: true
http.cors.allow-origin: "*"
```
* 参考链接：[链接](https://github.com/mobz/elasticsearch-head#enable-cors-in-elasticsearch)

### 问题2：vendor.js:7829 POST http://localhost:9200/_all/_search 406 (Not Acceptable)
* 背景：elasticsearch-head查看数据时报406错误码
* 解决
```
1、进入head安装目录；docker exec -it elasticsearch-head bash
2、打开文件夹_site，cd _site/
3、编辑vendor.js  共有两处
* 6886行   contentType: "application/x-www-form-urlencoded
改成
contentType: "application/json;charset=UTF-8"
* 7573行 var inspectData = s.contentType === "application/x-www-form-urlencoded" &&
改成
var inspectData = s.contentType === "application/json;charset=UTF-8" &&
```