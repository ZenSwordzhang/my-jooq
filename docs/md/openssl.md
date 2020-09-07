## 词语缩写

* CSR(Certificate Signing Request)
* CA(Certificate Authority)
* SAN(Subject Alternative Name)
* DNS(Domain Name System)
* SSL(Secure Sockets Layer)
* TSL(Transport Layer Security)
* ACME(Automatic Certificate Management Environment)


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


## 制作证书
* [参考链接](https://futu.im/article/https-certificate-2/)
* [参考链接](https://blog.csdn.net/witmind/article/details/78456660)
* [参考链接](https://medium.com/@superseb/get-your-certificate-chain-right-4b117a9c0fce)

### ------------------------CA证书-------------------------------

#### 1. 创建根证书密钥文件(自己做CA)ca.key：
* 方法1：
    * openssl genrsa -out ca.key 2048
* 方法:2：
    * 生成加密密钥
        * openssl genrsa -aes256 -out ca.key 2048
    * 去除密码
        * openssl rsa -in ca.key -out ca.key
```console
zsx@zsx:/data/test02/config/certs/tsl_ssl$ openssl genrsa -out ca.key 2048
Generating RSA private key, 2048 bit long modulus (2 primes)
...............................+++++
...................+++++
e is 65537 (0x010001)
```

#### 创建自签名根证书
* 2、3步骤合并
    * openssl req -x509 -sha256 -new -key ca.key -sha256 -days 3650 -out ca.crt

#### 2. 创建根证书的申请文件ca.csr：
* openssl req -new -key ca.key -out ca.csr -sha256
* 查看ca.csr信息
    * openssl req -text -noout -in ca.csr
```console
zsx@zsx:/data/test02/config/certs/tsl_ssl$ openssl req -new -key ca.key -out ca.csr -sha256
You are about to be asked to enter information that will be incorporated
into your certificate request.
What you are about to enter is what is called a Distinguished Name or a DN.
There are quite a few fields but you can leave some blank
For some fields there will be a default value,
If you enter '.', the field will be left blank.
-----
Country Name (2 letter code) [AU]:CN
State or Province Name (full name) [Some-State]:GD
Locality Name (eg, city) []:SZ
Organization Name (eg, company) [Internet Widgits Pty Ltd]:RYX
Organizational Unit Name (eg, section) []:YF

# 输入域名
Common Name (e.g. server FQDN or YOUR name) []:zsx

Email Address []:zsx@163.com

Please enter the following 'extra' attributes
to be sent with your certificate request
A challenge password []:
An optional company name []:
```

#### 3. 创建根证书ca.crt【用来签名其它证书，并配置在客户端信任列表，如果开启双向验证，也需要配置在服务端】：
* 配置单个域名
    * openssl x509 -req -in ca.csr -signkey ca.key -out ca.crt -sha256 -days 3650
```console
zsx@zsx:/data/test02/config/certs/tsl_ssl$ openssl x509 -req -in ca.csr -signkey ca.key -out ca.crt -sha256 -days 3650
Signature ok
subject=C = CN, ST = GD, L = SZ, O = RYX, OU = YF, CN = zsx, emailAddress = zsx@163.com
Getting Private key
```
* 查看证书详细信息
    * openssl x509 -noout -text -in ca.crt
    * keytool -printcert -file ca.crt
* 查看证书的颁发机构
    * openssl x509 -in ca.crt -noout -issuer
* 查看根证书的subject信息
    * openssl x509 -noout -subject -in ca.crt

### ------------------------Server端要下发的证书---------------------------------

#### 4. 创建服务器证书密钥server.key：
* 方法1：
    * openssl genrsa -out server.key 2048
* 方法:2：
    * 生成加密密钥
        * openssl genrsa -aes256 -out server.key 2048
    * 去除密码
        * openssl rsa -in server.key -out server.key

#### 5. 创建服务器证书的申请文件server.csr：
* openssl req -new -key server.key -out server.csr -sha256

#### 6. 创建服务器证书server.crt【配置在服务端，将会在客户端请求时下发给客户端】：
* 配置单个域名
    * openssl x509 -req -in server.csr -CA ca.crt -CAkey ca.key -CAserial ca.srl -CAcreateserial -out server.crt -extensions v3_req -sha256 -days 3650
```console
zsx@zsx:/data/test02/config/certs/tsl_ssl$ openssl x509 -req -in server.csr -CA ca.crt -CAkey ca.key -CAserial ca.srl -CAcreateserial -out server.crt -extensions v3_req -sha256 -days 3650
Signature ok
subject=C = CN, ST = GD, L = SZ, O = RYX, OU = YF, CN = zsx, emailAddress = zsx@163.com
Getting CA Private Key
```
* 配置多个域名
    * [com.ext](../tsl/com.ext)
    * openssl x509 -req -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out server.crt -extfile com.ext -sha256 -days 3650
* 验证证书
    * openssl verify -CAfile ca.crt server.crt
```console
zsx@zsx:/data/test02/config/certs/tsl_ssl$ openssl verify -CAfile ca.crt server.crt
server.crt: OK
```


### ------------------------Client端要下发的证书【开启双向验证后才需要】---------------------------------

#### 7. 创建客户端证书密钥文件client.key：
* 方法1：
    * openssl genrsa -out client.key 2048
* 方法:2：
    * 生成加密密钥
        * openssl genrsa -aes256 -out client.key 2048
    * 去除密码
        * openssl rsa -in client.key -out client.key

#### 8. 创建客户端证书的申请文件client.csr：
* openssl req -new -key client.key -out client.csr -sha256

#### 9. 创建一个自当前日期起有效期为两年的客户端证书client.crt【配置在客户端】：
* 配置单个域名
    * openssl x509 -req -in client.csr -CA ca.crt -CAkey ca.key -CAserial ca.srl -CAcreateserial -out client.crt -extensions v3_req -sha256 -days 3650
* 配置多个域名
    * openssl x509 -req -in client.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out client.crt -extfile com.ext -sha256 -days 3650
* 验证证书
    * openssl verify -CAfile ca.crt client.crt
```console
zsx@zsx:/data/test02/config/certs/tsl_ssl$ openssl verify -CAfile ca.crt client.crt
client.crt: OK
```

## tsl认证

### 单向认证指的是只有一个对象校验对端的证书合法性。
* 通常都是client来校验服务器的合法性
* 那么client需要一个ca.crt,服务器需要server.crt,server.key

### 双向认证指的是相互校验，服务器需要校验每个client,client也需要校验服务器。
* server 需要 server.key 、server.crt 、ca.crt
* client 需要 client.key 、client.crt 、ca.crt

### 认证流程
* 1.申请认证 > 2.审核信息 > 3.签发证书 > 4.返回证书 > 5.client验证证书 > 6.秘钥协商 > 7.数据传输

## 参考链接

### pkcs8
* [pkcs8](https://www.openssl.org/docs/man1.1.0/man1/pkcs8.html)