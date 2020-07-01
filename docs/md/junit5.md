## 操作

### 引入依赖
```build.gradle
testImplementation group: 'org.junit.jupiter', name: 'junit-jupiter', version: '5.7.0-M1'
```

### 设置方法执行顺序
* 使用@order注解：[TestExecutionOrderWithOrderAnnotation](../../src/test/java/com/zsx/junit5/TestExecutionOrderWithOrderAnnotation.java)
* 使用字母顺序：[TestExecutionOrderWithAlphanumeric](../../src/test/java/com/zsx/junit5/TestExecutionOrderWithAlphanumeric.java)
* 使用随机顺序：[TestExecutionOrderWithRandom](../../src/test/java/com/zsx/junit5/TestExecutionOrderWithRandom.java)

### 参数化
* [TestJunit5Parameterized](../../src/test/java/com/zsx/junit5/TestJunit5Parameterized.java)

### 设置java运行环境
* [TestJavaRuntimeEnvironmentConditions](../../src/test/java/com/zsx/junit5/TestJavaRuntimeEnvironmentConditions.java)

## 参考链接

### 官方
* [user-guide](https://junit.org/junit5/docs/current/user-guide/)

### 非官方
* [Java单元测试之JUnit5快速上手](https://juejin.im/post/5d80c66f51882539aa5adc10)