## 操作

### 验证私钥
* openssl rsa -in certs/logstash01/logstash01.key -check -noout
```console
RSA key ok
```

### 使用默认参数（具有256位密钥的AES和hmacWithSHA256）将私钥转换为PKCS＃8格式
* openssl pkcs8 -in test.key -topk8 -out test.pem

### 证书格式转换

#### 将私钥转换为PKCS＃8未加密格式
* openssl pkcs8 -in test.key -topk8 -nocrypt -out test1.pem

#### 
* openssl pkcs12 -in elastic-certificates.p12 -cacerts -nokeys -out elasticsearch-ca.pem

#### 将crt转换成pem
* openssl x509 -in ca.crt -out ca.pem

#### 根据key文件(http.key)生成ca文件(ca.crt)
* openssl req -new -x509 -key http.key -out ca.crt -days 3650

#### 根据csr文件、ca文件、key文件生成crt文件
* openssl x509 -req -days 3650 -in http.csr -CA ca.crt -CAkey http.key -CAcreateserial -out http.crt


## 参考链接

### pkcs8
* [pkcs8](https://www.openssl.org/docs/man1.1.0/man1/pkcs8.html)