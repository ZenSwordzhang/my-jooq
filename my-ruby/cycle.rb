#!/usr/bin/ruby

# frozen_string_literal: true

# while 语句
m1 = 0
while m1 < 5
  puts("Inside the loop m1 = #{m1}")
  m1 += 1
end

puts
# while 修饰符
m2 = 0
begin
  puts("Inside the loop m2 = #{m2}")
  m2 += 1
end while m2 < 5

puts
# until 修饰符
m3 = 0
begin
  puts("Inside the loop m3 = #{m3}")
  m3 += 1
end until m3 >= 5

puts
m4 = 0

# until 语句
until m4 >= 5
  puts("Inside the loop m4 = #{m4}")
  m4 += 1
end

# for 语句
puts
(0..5).each do |m5|
  puts "The value of the local variable m5 is #{m5}"
end

# each 语句
puts
(0..5).each do |m6|
  puts "The value of the local variable m6 is #{m6}"
end

# break 语句
puts
(0..5).each do |m7|
  break if m7 > 2

  puts "The value of the local variable m7 is #{m7}"
end

# next 语句
puts
(0..5).each do |m8|
  next if m8 < 3

  puts "The value of the local variable m8 is #{m8}"
end

# redo 语句
puts
i = 0
(0..5).each do |m9|
  break if i == 5

  puts "The value of the local variable m9 is #{m9}"
  if m9 == 3
    i += 1
    redo
  end
end

# retry 语句
puts
n = 0
begin
  (0..5).each do |m10|
    break if n == 3

    raise 'A test exception.' if m10 == 3

    puts "The value of the local variable m10 is #{m10}"
  end
rescue StandardError
  n += 1
  retry
end
