#!/usr/bin/ruby

# frozen_string_literal: true

# 整形
a1 = 123
# puts不带回车换行符
print(a1)
a2 = 123_455
print(' ')
print(a2)

a3 = 1_123_455
print(' ')
print(a3)
print(' ')

# 浮点型
f3 = 1_000_000.1
puts f3

message = format('Processing of the data has finished in %<time>d seconds',
                 time: 5)
# puts带回车换行符
puts message
# 多个参数
puts format('a1: %<first>d, a2: %<second>d', first: a1, second: a2)

puts format('%<first>d %<second>d', first: 20, second: 10)

puts "相乘 : #{24 * 60 * 60}"

name = 'Ruby'
puts (name + ', ok').to_s

# 数组
arr = ['abc', 123, 3.1415, 'efg', 'last']
arr.each do |i|
  puts i
end

# hash
colors = { red: 0xf00, green: 0x0f0, blue: 0x00f }
colors.each do |key, value|
  print key, ' is ', value, "\n"
end

# 范围
(10..15).each { |n| print n, ' ' }
