package dev.alexissdev.storage.redis.connection;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Represents an instance of a Redis connection, encapsulating a listener connection
 * and a connection pool. Provides convenient access to these resources for interacting
 * with Redis.
 */

public class JedisInstance {

    private final Jedis listenerConnection;
    private final JedisPool jedisPool;

    public JedisInstance(Jedis listenerConnection, JedisPool jedisPool) {
        this.listenerConnection = listenerConnection;
        this.jedisPool = jedisPool;
    }

    public Jedis getListenerConnection() {
        return listenerConnection;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }
}