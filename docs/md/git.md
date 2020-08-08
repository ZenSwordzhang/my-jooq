## 命令

### 分支合并主干代码（主干代码合并到分支）
* 1.切换到代码目录
    * cd /F/IdeaProjects/my-jooq
* 2.进入主干（master）
    * git checkout master
* 3.更新主干代码（master）
    * git pull
* 4.切换到分支（branch）
    * git checkout zsx-branch
* 5.在分支上合并主干代码（branch）
    * git merge master --squash
* 6.提交合并后的代码（branch）
    * git commit -m 'Merge remarks'
* 7.将代码推送到远程仓库（branch）
    * git push

### 主干合并分支代码（分支代码合并到主干）
* 1.切换到代码目录
    * cd /F/IdeaProjects/my-jooq
* 2.进入分支
    * git checkout zsx-branch
* 3.更新分支代码（branch）
    * git pull
* 4.切换到主干（branch）
    * git checkout master；
* 5.在主干上合并分支代码（master）
    * git merge branch --squash
* 6.提交合并后的代码（master）
    * git commit -m ‘合并备注’
* 7.将代码推送到远程仓库（master）
    * git push


## 设置

### 默认设置
* git config --global core.autocrlf false
* git config --global core.safecrlf true

### 提交时转换为LF，检出时转换为CRLF
* git config --global core.autocrlf true

### 提交时转换为LF，检出时不转换
* git config --global core.autocrlf input

### 提交检出均不转换
* git config --global core.autocrlf false

### 拒绝提交包含混合换行符的文件
* git config --global core.safecrlf true

### 允许提交包含混合换行符的文件
* git config --global core.safecrlf false

### 提交包含混合换行符的文件时给出警告
* git config --global core.safecrlf warn










