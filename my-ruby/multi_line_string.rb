#!/usr/bin/ruby
# frozen_string_literal: true

print <<EF
    这是第一种方式创建here document 。
    多行字符串。
EF

print <<"EF"
    这是第二种方式创建here document 。
    多行字符串。
EF

# 执行命令
print <<'EC'
    echo hi there
    echo lo there
EC

# 进行堆叠
print <<"FOO", <<"BAR"
    I said foo.
FOO
    I said bar.
BAR
