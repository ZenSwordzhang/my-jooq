#!/bin/bash

# ./check_redis.sh connected_clients

# rdcli -h 127.0.0.1 -p 7000 -a '1234' info # 有密码操作
# rdcli -h 127.0.0.1 -p 7000 info # 无密码操作
#

#$REDIS_CLI_COMMAND="redis-cli"
# 通过npm 安装了redis-client
REDIS_CLI_COMMAND="rdcli"
REDIS_HOST="127.0.0.1"
REDIS_PORT="7000"

ARGS=1

if [ $# -ne "$ARGS" ];then
    echo "Please input one args:"
fi

case $1 in
    connected_clients)
        result=$($REDIS_CLI_COMMAND -h $REDIS_HOST -p $REDIS_PORT info | grep -w "connected_clients" | awk -F':' '{print $2}')
            echo "$result"
            ;;
    used_memory_rss_human)
        result=$($REDIS_CLI_COMMAND -h $REDIS_HOST -p $REDIS_PORT info | grep -w "used_memory_rss_human" | awk -F':' '{print $2}')
            echo "$result"
            ;;
    used_memory_peak_human)
        result=$($REDIS_CLI_COMMAND -h $REDIS_HOST -p $REDIS_PORT info | grep -w "used_memory_peak_human" | awk -F':' '{print $2}')
            echo "$result"
            ;;
    total_connections_received)
        result=$($REDIS_CLI_COMMAND -h $REDIS_HOST -p $REDIS_PORT info | grep -w "total_connections_received" | awk -F':' '{print $2}')
            echo "$result"
            ;;
    instantaneous_ops_per_sec)
        result=$($REDIS_CLI_COMMAND -h $REDIS_HOST -p $REDIS_PORT info | grep -w "instantaneous_ops_per_sec" | awk -F':' '{print $2}')
            echo "$result"
            ;;
    instantaneous_input_kbps)
        result=$($REDIS_CLI_COMMAND -h $REDIS_HOST -p $REDIS_PORT info | grep -w "instantaneous_input_kbps" | awk -F':' '{print $2}')
            echo "$result"
            ;;
    instantaneous_output_kbps)
        result=$($REDIS_CLI_COMMAND -h $REDIS_HOST -p $REDIS_PORT info | grep -w "instantaneous_output_kbps" | awk -F':' '{print $2}')
            echo "$result"
            ;;
    rejected_connections)
        result=$($REDIS_CLI_COMMAND -h $REDIS_HOST -p $REDIS_PORT info | grep -w "rejected_connections" | awk -F':' '{print $2}')
            echo "$result"
            ;;
    expired_keys)
        result=$($REDIS_CLI_COMMAND -h $REDIS_HOST -p $REDIS_PORT info | grep -w "expired_keys" | awk -F':' '{print $2}')
            echo "$result"
            ;;
    evicted_keys)
        result=$($REDIS_CLI_COMMAND -h $REDIS_HOST -p $REDIS_PORT info | grep -w "evicted_keys" | awk -F':' '{print $2}')
            echo "$result"
            ;;
    keyspace_hits)
        result=$($REDIS_CLI_COMMAND -h $REDIS_HOST -p $REDIS_PORT info | grep -w "keyspace_hits" | awk -F':' '{print $2}')
            echo "$result"
            ;;
    keyspace_misses)
        result=$($REDIS_CLI_COMMAND -h $REDIS_HOST -p $REDIS_PORT info | grep -w "keyspace_misses" | awk -F':' '{print $2}')
            echo "$result"
            ;;

        *)
        echo "Usage:$0(connected_clients|used_memory_rss_human|used_memory_peak_human|instantaneous_ops_per_sec|instantaneous_input_kbps|instantaneous_output_kbps|rejected_connections|expired_keys|evicted_keys|keyspace_hits|keyspace_misses)"
        ;;
esac