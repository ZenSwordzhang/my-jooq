#!/usr/bin/ruby

# frozen_string_literal: true

# if...else 语句
x1 = 1
if x1 > 2
  puts 'x1 is greater than 2'
elsif (x1 <= 2) && (x1 != 0)
  puts 'x1 is 1'
else
  puts "I can't guess the number"
end

# if 修饰符
x2 = 1
print "x2=#{x2}\n" if x2

# unless 语句
x3 = 1
unless x3 > 2
  puts 'x3 is less than 2'
else
  puts 'x3 is greater than 2'
end

# unless 修饰符 如果 conditional 为假，则执行 code
x4 =  1
print "1 -- Value is set\n" if x4
print "2 -- Value is set\n" unless x4

x5 = false
print "3 -- Value is set\n" unless x5

# case 语句
age = 5
case age
when 0..2
  puts 'baby'
when 3..6
  puts 'little child'
when 7..12
  puts 'child'
when 13..18
  puts 'youth'
else
  puts 'adult'
end

puts
puts [].nil?
puts [''].nil?
puts nil.nil?
puts ['abc'].nil?
puts 'abc'.nil?
puts ''.nil?
puts ' '.nil?

puts
puts [].empty?
puts [''].empty?
puts ['abc'].empty?
puts 'abc'.empty?
puts ''.empty?
puts ' '.empty?

puts
puts 1 != 2
puts !1.nil?
puts 1 != ''
