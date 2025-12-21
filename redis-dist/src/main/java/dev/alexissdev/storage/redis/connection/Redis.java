package dev.alexissdev.storage.redis.connection;

import com.google.gson.Gson;
import dev.alexissdev.storage.redis.messenger.Messenger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Closeable;

public interface Redis
        extends Closeable {

    /**
     * Retrieves the {@code Messenger} instance associated with the Redis connection.
     * The {@code Messenger} facilitates the creation and management of communication
     * channels for message exchange across servers or components.
     *
     * @return the {@code Messenger} instance used for managing communication channels
     */

    Messenger getMessenger();

    /**
     * Retrieves the underlying connection pool used to manage Redis connections.
     * This pool provides a thread-safe way to acquire and release Redis connections.
     *
     * @return the {@code JedisPool} instance representing the connection pool
     */

    JedisPool getRawConnection();

    /**
     * Retrieves the dedicated Redis connection used for listening to messages
     * or events. This connection is typically separate from the connection
     * pool to ensure uninterrupted operation for subscription-based features
     * like Pub/Sub.
     *
     * @return a {@code Jedis} instance representing the listener connection
     */

    Jedis getListenerConnection();

    /**
     * Provides a builder interface for constructing instances of the {@code Redis} object.
     * This interface defines methods to configure various components and parameters required
     * to initialize a Redis connection, such as the parent communication channel, server
     * identity, Gson instance for serialization, and Redis connection resources.
     * <p>
     * The {@code Redis.Builder} is designed to ensure a customizable and fluent way of
     * constructing Redis objects by allowing chaining of configuration methods.
     */

    interface Builder {

        /**
         * Sets the name of the parent communication channel for the Redis connection.
         * This channel is used for message routing and communication within the Redis-based system.
         *
         * @param parentChannel the name of the parent channel to use for communication; must not be null or empty
         * @return the {@code Builder} instance for method chaining
         */

        Builder setParentChannel(String parentChannel);

        /**
         * Sets the identifier for the Redis server. The server ID is used to uniquely
         * identify the instance of the Redis connection and may be utilized for purposes
         * such as distinguishing between different Redis deployments in a distributed environment.
         *
         * @param id the unique identifier for the Redis server; must not be null or empty
         * @return the {@code Builder} instance for method chaining
         */

        Builder setServerId(String id);

        /**
         * Configures the {@code Gson} instance to be used for JSON serialization and deserialization
         * when interacting with the Redis connection. This method allows the user to provide a
         * custom {@code Gson} instance with specific configurations if needed.
         *
         * @param gson the {@code Gson} instance to use for JSON operations; must not be null
         * @return the {@code Builder} instance for method chaining
         */

        Builder setGson(Gson gson);

        /**
         * Configures this builder to use the specified {@link JedisPool} instance for managing Redis connections.
         * The provided {@code JedisPool} serves as the connection pool for acquiring and releasing Redis connections.
         *
         * @param jedisPool the {@code JedisPool} instance to be used; must not be null
         * @return the {@code Builder} instance for method chaining
         */

        Builder setJedisPool(JedisPool jedisPool);

        /**
         * Configures this builder to use the specified {@code JedisInstance} for managing Redis connections.
         * The provided {@code JedisInstance} encapsulates a listener connection and a connection pool,
         * which are used for interacting with Redis.
         *
         * @param jedis the {@code JedisInstance} to be used; must not be null
         * @return the {@code Builder} instance for method chaining
         */

        Builder setJedis(JedisInstance jedis);

        /**
         * Configures the {@code Jedis} instance to be used as the listener connection for the Redis system.
         * This connection is intended for subscribing to and listening for messages on Redis channels.
         *
         * @param listenerConnection the {@code Jedis} instance to be used as the listener connection; must not be null
         * @return the {@code Builder} instance for method chaining
         */

        Builder setListenerConnection(Jedis listenerConnection);

        /**
         * Constructs and returns a fully configured {@code Redis} instance based on the parameters
         * previously set in the {@code Builder}. The returned {@code Redis} object facilitates
         * communication with a Redis server, providing mechanisms for managing communication
         * channels, connection pools, and listener connections.
         *
         * @return a configured {@code Redis} instance ready for use
         */

        Redis build();
    }
}