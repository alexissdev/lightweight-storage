package dev.alexissdev.storage.redis.connection;

import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class provides a wrapper around Redis operations for caching data.
 * It facilitates storing and retrieving data from Redis hash tables, along with
 * support for setting expiration times and managing keys and values efficiently.
 */

public class RedisCache {

    private final String name;
    private final JedisPool jedisPool;

    public RedisCache(String name, JedisPool jedisPool) {
        this.name = name;
        this.jedisPool = jedisPool;
    }

    /**
     * Retrieves all values from a specified Redis hash table.
     *
     * @param table the name of the table (hash) from which to fetch all values; must not be null
     * @return a list of strings representing all the values in the specified Redis hash table
     */

    public List<String> getAllValues(String table) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hvals(makeTable(table));
        }
    }

    /**
     * Retrieves all keys from a specified Redis hash table.
     *
     * @param table the name of the table (hash) from which to fetch all keys; must not be null
     * @return a set of strings representing all the keys in the specified Redis hash table
     */

    public Set<String> getAllKeys(String table) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hkeys(makeTable(table));
        }
    }

    /**
     * Retrieves all key-value pairs from a specified Redis hash table.
     *
     * @param table the name of the table (hash) from which to fetch all key-value pairs; must not be null
     * @return a map containing all key-value pairs from the specified Redis hash table
     */

    public Map<String, String> getAll(String table) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hgetAll(makeTable(table));
        }
    }

    /**
     * Sets a key-value pair in a specified Redis hash table and optionally applies an expiration time.
     * If the expiration time is greater than zero, it sets the TTL (Time to Live) for the hash table.
     * If the expiration time is zero or negative, no expiration is applied.
     *
     * @param table   the name of the Redis hash table where the key-value pair will be stored; must not be null
     * @param key     the key to insert or update in the hash table; must not be null
     * @param value   the value to associate with the specified key; must not be null
     * @param seconds the expiration time in seconds for the hash table; if greater than 0, the TTL is set; otherwise, no expiration is applied
     */

    public void set(String table, String key, String value, long seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            String tableName = makeTable(table);
            jedis.hset(tableName, key, value);

            if (seconds > 0) {
                jedis.expire(tableName, seconds);
            }
        }
    }

    /**
     * Sets a key-value pair in a specified Redis hash table.
     * This method delegates to the overloaded set method which includes an optional expiration time.
     * No expiration time is applied when this method is called.
     *
     * @param table the name of the Redis hash table where the key-value pair will be stored; must not be null
     * @param key   the key to insert or update in the hash table; must not be null
     * @param value the value to associate with the specified key; must not be null
     */

    public void set(String table, String key, String value) {
        set(table, key, value, -1);
    }

    /**
     * Retrieves a value associated with the given key from a specified Redis hash table.
     *
     * @param table the name of the Redis hash table from which the value will be retrieved; must not be null
     * @param key   the key whose associated value is to be retrieved; must not be null
     * @return the value associated with the specified key in the Redis hash table, or null if the key does not exist
     */

    public @Nullable String get(String table, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hget(makeTable(table), key);
        }
    }

    /**
     * Deletes a key-value pair from a specified Redis hash table.
     *
     * @param table the name of the Redis hash table from which the key will be removed; must not be null
     * @param key   the key to delete from the hash table; must not be null
     */

    public void del(String table, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hdel(makeTable(table), key);
        }
    }

    /**
     * Constructs a fully qualified table name by appending the class name to the specified table name.
     *
     * @param table the name of the table to be prefixed with the class name; must not be null
     * @return a string representing the fully qualified table name in the format "name:table"
     */

    public String makeTable(String table) {
        return name + ":" + table;
    }
}