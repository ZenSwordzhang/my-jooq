#!/usr/bin/sh

port=1000
echo 1${port}

printf 1%d\\n ${port}
printf "Hello, %s\n" "$NAME"

#for i in `seq 7000 7005`;
#for i in {7000..7005}
#for((i=7000; i<7006; i++));
# lt <
# le <=
# gt >
# -ge >=
# -eq =
# 上面写法会提示警告，替换为
for i in $(seq 7000 7005)
do
  printf "%s\n" "${i}"
done
printf "\n"


for i in 7000 7001 7002 7003 7004 7005
do
  printf "%s\n" "${i}"
done
printf "\n"

i=7000
while [ $i -lt 7006 ]
do
  i=$((i + 1))
  printf "%s\n" "${i}"
done
printf "\n"

