#!/usr/bin/ruby

# frozen_string_literal: true

# 类上必须加注释，否则提示警告：
# RuboCop: Missing top-level class documentation comment. [Style/Documentation]
class Person
  attr_reader :name, :age
  # 定义实例方法
  def hello
    puts 'Hello Ruby'
  end
end

# 不带参数创建对象
person = Person.new
puts person
# 调用实例方法
person.hello

# 消费者类继承人类
class Customer < Person

  # @@ 开头的是类变量，初始化类变量
  @@count = 0
  # class_variable_set(:@@count, 0)

  # 调用带参数的类的 new 方法时执行
  def initialize(id, name, addr)
    # @ 开头的是实例变量
    @cust_id = id
    @cust_name = name
    @cust_addr = addr
  end

  def self.count
    @@count
  end

  def display_details
    puts "Customer id #{@cust_id}"
    puts "Customer name #{@cust_name}"
    puts "Customer address #{@cust_addr}"
  end

  def self.total_count
    @@count += 1
    puts "Total number of customers: #{@@count}"
  end

end

# 带参数创建对象
cust1 = Customer.new('1', 'zhangsan', '北京')
cust2 = Customer.new('2', 'lisi', '深圳')
puts cust1.display_details, Customer.total_count,
     cust2.display_details, Customer.total_count
