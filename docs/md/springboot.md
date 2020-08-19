## 异常

### 异常优先级

#### 请求路径存在时
* ExceptionHandler > DefaultErrorAttributes

#### 请求路径不存在时
* AbstractErrorController > DefaultErrorAttributes > ExceptionHandler
    * DefaultErrorAttributes
        * getErrorAttributes() > getError()








## 参考链接

### 自定义异常
* [Exception](https://thepracticaldeveloper.com/2019/09/09/custom-error-handling-rest-controllers-spring-boot/)

