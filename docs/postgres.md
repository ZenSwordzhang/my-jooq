## 问题

### 问题：FATAL:  data directory "/var/lib/postgresql/data" has wrong ownership
* 详情：
```log
2020-05-28 03:49:42.637 UTC [82] HINT:  The server must be started by the user that owns the data directory.
postgres   | child process exited with exit code 1
postgres   | initdb: removing contents of data directory "/var/lib/postgresql/data"
postgres exited with code 1

```
* 背景：win10下docker 启动postgres容器报错
```docker-compose-postgres.yml
version: '3.1'

services:
  # 服务器名
  db:
    image: postgres:13
    container_name: postgres
    # restart: always
    command: -c 'config_file=/etc/postgresql/postgresql.conf'
    volumes:
        - /d/usr/share/postgresql/data:/var/lib/postgresql/data
        - /d/usr/share/postgresql/log:/var/log/postgresql
        # # 配置文件默认路径 /var/lib/postgresql/data/postgresql.conf
        - /d/usr/share/postgresql/my-postgres.conf:/etc/postgresql/postgresql.conf
    environment:
      POSTGRES_USER: test
      POSTGRES_PASSWORD: test
      # POSTGRES_DB: test
    ports:
      - 5433:5432
      
  adminer:
    image: adminer:4.7.7-standalone
    container_name: adminer
    # restart: always
    ports:
      - 8080:8080
volumes:
  pgdata:
     external: true

```
* 原因：postgres必须以目录的所有者身份运行（无论读写是否成功），并且不能以root身份运行
* 解决：
    * 1.手动创建数据卷：docker volume create --name=pgdata
    * 2.挂载数据卷部分修改
    ```
    - /d/usr/share/postgresql/data:/var/lib/postgresql/data
    修改为：
    - pgdata:/var/lib/postgresql/data
    ```
    * 3.文件末尾部分声明数据卷外部可用
    ```
    volumes:
      pgdata:
         external: true
    ```
    * 修改后的文件内容如下
    ``` docker-compose-postgres.yml
    version: '3.1'
    
    services:
      # 服务器名
      db:
        image: postgres:13
        container_name: postgres
        # restart: always
        command: -c 'config_file=/etc/postgresql/postgresql.conf'
        volumes:
            - pgdata:/var/lib/postgresql/data
            - /d/usr/share/postgresql/log:/var/log/postgresql
            # # 配置文件默认路径 /var/lib/postgresql/data/postgresql.conf
            - /d/usr/share/postgresql/my-postgres.conf:/etc/postgresql/postgresql.conf
        environment:
          POSTGRES_USER: test
          POSTGRES_PASSWORD: test
          # POSTGRES_DB: test
        ports:
          - 5433:5432
          
      adminer:
        image: adminer:4.7.7-standalone
        container_name: adminer
        # restart: always
        ports:
          - 8080:8080
    volumes:
      pgdata:
         external: true
    
    ```
* 参考
    * [链接1](https://forums.docker.com/t/data-directory-var-lib-postgresql-data-pgdata-has-wrong-ownership/17963)
    * [链接2](https://github.com/graphprotocol/graph-node/issues/755)

### 问题2：ERROR: Named volume "pgdata:/var/lib/postgresql/data:rw" is used in service "db" but no declaration was found in the volumes section.
* 背景：win10下docker 启动postgres容器报错
```docker-compose-postgres.yml
version: '3.1'

services:
  # 服务器名
  db:
    image: postgres:13
    container_name: postgres
    # restart: always
    command: -c 'config_file=/etc/postgresql/postgresql.conf'
    volumes:
        - pgdata:/var/lib/postgresql/data
        - /d/usr/share/postgresql/log:/var/log/postgresql
        # # 配置文件默认路径 /var/lib/postgresql/data/postgresql.conf
        - /d/usr/share/postgresql/my-postgres.conf:/etc/postgresql/postgresql.conf
    environment:
      POSTGRES_USER: test
      POSTGRES_PASSWORD: test
      # POSTGRES_DB: test
    ports:
      - 5433:5432
```
* 原因：缺少数据声明外部可用
* 解决：
    * 文件末尾部分声明数据卷外部可用
    ```
    volumes:
      pgdata:
         external: true
    ```
















