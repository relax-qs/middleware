package com.lark.middleware.util.connection;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

/***
 * 海航改造后的连接工厂，去掉每次连接select db的操作
 * 
 * @author liguogang
 * @version $Id: HNJedisConnectionFactory.java, v 0.1 2016年9月17日 下午4:51:25 liguogang Exp $
 */
public class HNJedisConnectionFactory extends JedisConnectionFactory {
    protected Pool<Jedis> createRedisPool() {
        Pool<Jedis> pool = new JedisPool(getPoolConfig(), getShardInfo().getHost(), getShardInfo().getPort(), getTimeout(), getShardInfo().getPassword(),
            this.getDatabase());
        this.setDatabase(0);
        return pool;
    }

}
