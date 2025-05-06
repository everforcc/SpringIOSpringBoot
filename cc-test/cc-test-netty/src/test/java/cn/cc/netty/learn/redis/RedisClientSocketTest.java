package cn.cc.netty.learn.redis;

import cn.cc.netty.learn.redis.socket.RedisClientSocket;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class RedisClientSocketTest {

    @Test
    public void commandTest() {
        try {
            RedisClientSocket.connect();
            // ZADD命令的返回值仅表示是否添加了新成员，而不是分数是否被更新。如果成员已存在且分数被更新，返回值仍然是0
            List<String> commandList = Arrays.asList(
                    "get name"
            );
            RedisClientSocket.flow(auth_String());
            RedisClientSocket.flow(commandList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> auth_String() {
        return Arrays.asList(
                "auth ruoyi123"
        );
    }

    public List<String> command_String() {
        return Arrays.asList(
                "ping",
                "keys *",
                "EXISTS k",
                "EXISTS c",
                "get k"
        );
    }

    public List<String> command_Hash() {
        return Arrays.asList(
                "HMSET runoobkeyset name 'redis-tutorial' description 'redis-basic-commands-for-caching' likes 20 visitors 23000",
                "HGETALL runoobkeyset"
        );
    }

    public List<String> command_List() {
        return Arrays.asList(
                "LPUSH runoobkey redis",
                "LPUSH runoobkey mongodb",
                "LPUSH runoobkey mysql",
                "LRANGE runoobkey 0 10",
                "LINDEX runoobkey 3"
        );
    }

    public List<String> command_Set() {
        return Arrays.asList(
                "SADD runoobkey_set redis",
                "SADD runoobkey_set mongodb",
                "SADD runoobkey_set mysql",
                "SADD runoobkey_set mysql",
                "SMEMBERS runoobkey_set"
        );
    }

    public List<String> command_Sorted_Set() {
        return Arrays.asList(
                "ZADD runoobkey_sorted_set 1 redis",
                "ZADD runoobkey_sorted_set 2 mongodb",
                "ZADD runoobkey_sorted_set 3 mysql",
                "ZADD runoobkey_sorted_set 3 mysql",
                "ZADD runoobkey_sorted_set 4 mysql",
                "ZRANGE runoobkey_sorted_set 0 10 WITHSCORES"
        );
    }

}
