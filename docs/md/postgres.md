## 操作

### 创建扩展
* CREATE EXTENSION pg_stat_statements;
* CREATE EXTENSION pg_stat_statements SCHEMA public;

### 删除扩展
* DROP EXTENSION pg_stat_statements;

### 重新加载配置
* SELECT pg_reload_conf();

### 连接数据库
* psql postgres://username:password@host:port/dbname
    * psql postgres://postgres:1234@localhost:5432/postgres
* psql -U username -h hostname -p port -d dbname
    * psql -U postgres -h localhost -p 5432 -d postgres
    * psql -U postgres

### 退出数据库连接
* exit
* Ctrl + c

### 查看search_path
* SHOW search_path;

### 给当前会话设置search_path
* SET search_path TO postgres, public;

### 给数据库永久设置search_path
* ALTER DATABASE <database_name> SET search_path TO schema1,schema2;

### 给用户或角色设置search_path
* ALTER USER <user_name> SET search_path TO schema1,schema2;

### 给角色设置search_path
* ALTER ROLE <role_name> SET search_path TO schema1,schema2;

### 给特定的数据库设置用户的search_path
* ALTER ROLE <role_name> IN DATABASE <db_name> SET search_path TO schema1,schema2;

### 重置search_path
* ALTER DATABASE <database_name> RESET search_path;
* ALTER ROLE <role_name> in DATABASE <database_name> RESET search_path;

### 查看扩展
* SELECT * FROM pg_available_extensions WHERE name = 'pg_stat_statements' and installed_version is not null;


## 参考链接

### 配置文件参数说明
* [runtime-config-logging](https://www.postgresql.org/docs/current/runtime-config-logging.html)

### PostgreSQL 数据库日志文件
* [PostgreSQL](https://docs.amazonaws.cn/AmazonRDS/latest/AuroraUserGuide/USER_LogAccess.Concepts.PostgreSQL.html)

### pgBadger - A fast PostgreSQL Log Analyzer
* [pgBadger](https://access.crunchydata.com/documentation/pgbadger/latest/)

### sql state
* [errcodes-appendix](https://www.postgresql.org/docs/current/errcodes-appendix.html)

### pg
* [pg-settings](https://www.postgresql.org/docs/current/view-pg-settings.html)
* [pg_stat_statements](https://www.postgresql.org/docs/current/pgstatstatements.html)
* [ddl-schemas](https://www.postgresql.org/docs/12/ddl-schemas.html)








