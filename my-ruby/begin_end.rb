#!/usr/bin/ruby

# frozen_string_literal: true

puts '这是主 Ruby 程序'

at_exit do
  puts '停止 Ruby 程序'
end

# 避免使用
BEGIN {
  puts '初始化 Ruby 程序'
}
