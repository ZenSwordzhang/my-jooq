### 删除数据库所有表
```
gradle flywayClean
```
### 迁移数据库
```
gradle flywayMigrate
```
### 校验新版本文件是否有冲突
```
gradle flywayValidate
```
### 查看数据库状态
```
gradle flywayInfo
```
### 修复数据库（删除失败的版本，修复checksum值）
```
gradle flywayRepair
```
### 设置某一版本为基础版本，该版本及之前的不会再被执行
```
gradle flywayBaseline
```
### 需要Flyway企业版，版本回滚
```
gradle flywayUndo
```