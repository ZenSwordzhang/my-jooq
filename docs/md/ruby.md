## 代码分析器

### rubocop是Ruby的代码分析器

### 查看rubocop 版本
* rubocop -v
* rubocop -V

### 修复命令（切换到ruby文件所在目录）
* 修复目录下的所有ruby文件：rubocop -a
* 修复指定文件：rubocop -a first_ruby.rb

### 问题3：RuboCop: Unnecessary utf-8 encoding comment. [Style/Encoding]
* 原因：ruby2.0+中，utf-8是默认的源文件编码
    * 仅当存在非ASCII字符时才强制执行编码注释，否则始终报告违规
    * 从来不在所有文件中强制执行编码注释
    * 在所有文件中强制不使用编码注释
* 解决：移除编码注释行

### 问题3：RuboCop: Use uppercase heredoc delimiters. [Naming/HeredocDelimiterCase]
* 背景：win10下ruby文件提示语法警告
* 原因：
* 解决：<<"bar"改成大写"BAR"

### 问题4：RuboCop: Use meaningful heredoc delimiters. [Naming/HeredocDelimiterNaming]
* 背景：win10下ruby文件提示语法警告
* 原因：默认情况下，它不允许使用END和EO*
* 解决：<<'EOC'修改成其他，如<<'EC'

### 问题5：RuboCop: Use only ascii symbols in comments. [Style/AsciiComments]
* 背景：win10下ruby文件提示语法警告
* 原因：注释中仅使用ascii符号
* 解决：
    * 方法1：改成英文注释
    * 方法2：禁用AsciiComments，在E:\Ruby27-x64\lib\ruby\gems\2.7.0\gems\rubocop-0.83.0\config\default.yml文件修改如下配置
    ```
    Style/AsciiComments:
        Enabled: false
    ```
### 问题6：RuboCop: Avoid the use of `END` blocks. Use `Kernel#at_exit` instead. [Style/EndBlock]    
* 解决：
```
END {
   puts "停止 Ruby 程序"
}
修改为
at_exit do
  puts '停止 Ruby 程序'
end
```

### 问题7：RuboCop: Avoid the use of `BEGIN` blocks. [Style/BeginBlock] 
* 解决：避免使用

### 问题8：RuboCop: Favor `format` over `String#%`. [Style/FormatString]
* 解决：
```
message = 'Processing of the data has finished in %d seconds' % [time]
修改为
message = format('Processing of the data has finished in %d seconds', time)
```

### 问题9：RuboCop: Prefer annotated tokens (like `%<foo>s`) over unannotated tokens (like `%s`). [Style/FormatStringToken]
* 解决：
```
message = format('Processing of the data has finished in %d seconds', time)
修改为
message = format('Processing of the data has finished in %<first>d seconds',
                 first: time)
```

### 问题10：Interpolated instance variable doesn't have braces
原因：插入的实例变量没有花括号
解决：
```
puts "Customer name #@cust_name"
添加花括号，修改为
puts "Customer name #{@cust_name}"
```

## 语法
### 命名
* Ruby 强制了一些命名约定：
    * 常量使用大写；
    * 美元符号($)开头的是全局变量； 
    * @ 开头的是实例变量； 
    * @@ 开头的是类变量。 
    * 方法名总是以小写字母开头

### 函数
* public是公开的。
* private 表示只有非明确指定接收者（receiver）才允许调用。
* 私有方法调用只允许 self 为接收者

### 类
* Ruby 的类是开放的

### 方法
* Ruby 的方法名允许以问号或感叹号结尾
* 习惯上，以问号结尾的方法返回布尔值（如：如果接收者为空的话 Array#empty? 返回 true ）
* 潜在“危险”方法（如修改 self 或参数的方法，exit! 等）以感叹号结尾。
* 也不是所有修改参数的方法以感叹号结尾。 比如 Array#replace 方法将当前列表替换成另一个列表。
* 类中的每个方法是以关键字 def 开始，后跟方法名。
* 方法名总是以小写字母开头。在 Ruby 中，可以使用关键字 end 来结束一个方法。





















