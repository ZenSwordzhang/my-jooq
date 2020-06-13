### 问题一：Could not target platform: 'Java SE 14' using tool chain: 'JDK 11 (11)'.
* 解决方法：
    * [步骤一](../img/jdk/jdk-setting-1.jpg)
    * [步骤二](../img/jdk/jdk-setting-2.jpg)
    * [步骤三](../img/jdk/jdk-setting-3.jpg)
    * [步骤四](../img/jdk/jdk-setting-4.jpg)

### 问题二： Process 'command 'E:\jdk\jdk-14.0.1\bin\java.exe'' finished with non-zero exit value -1
* 原因：配置了properties属性
```
jdbc {
    driver = 'org.postgresql.Driver'
    url = 'jdbc:postgresql://localhost:5432/my-jooq'
    user = 'postgres'
    password = '1234'
    properties {
        property {
            key = 'ssl'
            value = 'true'
        }
    }
}
```
* 解决方法：移除该属性值配置
```
jdbc {
    driver = 'org.postgresql.Driver'
    url = 'jdbc:postgresql://localhost:5432/my-jooq'
    user = 'postgres'
    password = '1234'
}
```

### 问题三：Could not get unknown property 'generateMyJooqSchemaSource' for task set of type org.gradle.api.internal.tasks.DefaultTaskContainer
* 任务名称不正确，修改为跟jooq插件名称一致
* [任务名称不正确](../img/jooq-1.jpg)



