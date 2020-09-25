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

### 查看所有分支
* git branch -a

### 在本地新建分支
* git branch branchName
    * git branch home_zsx

### 新创建分支并切换
* git checkout -b zsx company_zsx

### 切换到你的新分支
* git checkout branchName

### 将新分支发布在github上
* git push origin branchName

### 在本地删除一个分支
* git branch -d branchName

### 在github远程端删除一个分支
* git push origin :branchName

### 添加所有文件
* git add . 

### 修改commit注释
* git commit --amend

### 版本回退

#### 回退到上一个版本（取消commit操作）
* git reset --soft HEAD^

#### 回退n次commit
* git reset --soft HEAD^n

#### 参数
* --mixed：不删除工作空间改动代码，撤销commit，并且撤销git add 操作
    * 这个为默认参数，git reset --mixed HEAD^ 和 git reset HEAD^ 效果一样

* --soft：不删除工作空间改动代码，撤销commit，不撤销git add 操作

* --hard：删除工作空间改动代码，撤销commit，撤销git add . 
    * 注意完成这个操作后，就恢复到了上一次的commit状态



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










