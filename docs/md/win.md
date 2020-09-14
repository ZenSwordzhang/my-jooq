## 命令

### powershell、cmd通用命令

#### 查看端口占用
* netstat -aon | findstr "8088"

#### 杀掉进程
* taskkill /f /t /pid xxx

#### 查看证书管理界面
* certmgr.msc


### powershell下命令

#### 查看硬盘信息
* Get-PhysicalDisk


### cmd下命令

#### windows删除服务
* sc delete service_name

