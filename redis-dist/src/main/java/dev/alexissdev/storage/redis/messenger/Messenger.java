package dev.alexissdev.storage.redis.messenger;

import com.google.gson.reflect.TypeToken;
import dev.alexissdev.storage.redis.channel.Channel;

import java.io.Closeable;

public interface Messenger extends Closeable {

    /**
     * Retrieves a communication channel associated with a specific name and payload type.
     * The channel allows message exchange between servers or components, utilizing the
     * provided payload type for operations.
     *
     * @param <T>  the type of message payload that the channel will handle
     * @param name the unique name of the channel to be retrieved
     * @param type the {@code TypeToken} representing the type of payload associated with the channel
     * @return the {@code Channel} instance associated with the specified name and payload type
     * @throws IllegalArgumentException if a channel with the specified name exists but the payload type does not match
     */

    <T> Channel<T> getChannel(String name, TypeToken<T> type);

    /**
     * Retrieves a communication channel associated with a specific name and payload type.
     * This method provides a shorthand way to obtain a channel using a {@code Class} reference,
     * delegating to the more general method using a {@code TypeToken}.
     *
     * @param <T>  the type of message payload that the channel will handle
     * @param name the unique name of the channel to be retrieved
     * @param type the {@code Class} representing the type of payload associated with the channel
     * @return the {@code Channel} instance associated with the specified name and payload type
     */

    default <T> Channel<T> getChannel(String name, Class<T> type) {
        return getChannel(name, TypeToken.get(type));
    }
}