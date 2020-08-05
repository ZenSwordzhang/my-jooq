#!/usr/bin/python
# -*- coding: UTF-8 -*-


write_bytes = (7402876928, 7404462080, 7406030848, 7407558656, 7409119232,
               7410675712, 7412215808, 7413805056, 7415074816, 7416619008)
read_bytes = (647639040, 650551296, 660893696, 661016576, 661073920, 661479424, 661565440, 662769664)

print("write speed:")
m = 7401304064
for i in range(len(write_bytes)):
    write_speed = (write_bytes[i] - m) / 1024 / 10
    print(f'{write_speed} kb/s')
    m = write_bytes[i]

print()
print("read speed:")
n = 647639040
for i in range(len(read_bytes)):
    read_speed = (read_bytes[i] - n) / 1024 / 10
    print(f'{read_speed} kb/s')
    n = read_bytes[i]
