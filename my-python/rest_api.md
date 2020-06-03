## 问题
## 问题1
* 详情
```
b'A request was sent to this URL (http://localhost:8088/user) but a redirect was issued automatically by the routing system to "http://localhost:8088/user/".  The URL was defined with a trailing slash so Flask will automatically redirect to the URL with the trailing slash if it was accessed without one.  Make sure to directly send your POST-request to this URL since we can\'t make browsers or HTTP clients redirect with form data reliably or without user interaction.\n\nNote: this exception is only raised in debug mode'
```
* 背景：通过postman，执行http://localhost:8088/user发送post请求时报错
* 解决(注：末尾斜杠)：http://localhost:8088/user/

## Python编写RestApi

### 1.升级pip
* python3 -m pip install --upgrade pip

### 2.安装flask
* pip3 install flask

### 3.编写脚本
* [hello_world_page](helloworld_page.py)

### 4.查看请求
* 打开浏览器：http://127.0.0.1:5000/

### 5.简单RestApi编写
* [rest_api](rest_api.py)














